package cn.cs.scu.analyse

import java.util.Date

import cn.cs.scu.constants.Constants
import cn.cs.scu.dao.factory.DaoFactory
import cn.cs.scu.dao.implement.{AdDaoImplement, ProvinceClickDaoImplement}
import cn.cs.scu.domain.{Ad, Blacklist, ProvinceTop3Ad}
import cn.cs.scu.javautils.StringUtils
import cn.cs.scu.scalautils.DateUtils
import org.apache.spark.HashPartitioner
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.json.JSONObject

import scala.collection.mutable.ListBuffer

/**
  * Created by zhangchi on 17/3/15.
  */
object RealTimeAnalyse {

  /**
    * 返回原始数据
    *
    * @param streamingContext
    * @param data
    * @return
    */
  def getOriginData(streamingContext: StreamingContext,
                    data: ReceiverInputDStream[(String, String)]
                   ): DStream[(String, String, String, String, String)] = {

    val logs = data.map(_._2).flatMap(_.split("\n")).map(row => {
      row.split("\t").toBuffer
    })

    logs.map(r => {
      (DateUtils.getDate(r.head.toLong), r(1), r(2), r(3), r(4))
    })

  }


  /**
    * 计算用户点击广告次数
    * (date=2017-03-16|userId=487|adId=9,1)
    *
    * @param streamingContext
    * @param originData
    * @return
    */
  def countUserClickTimes(streamingContext: StreamingContext,
                          originData: DStream[(String, String, String, String, String)]
                         ): DStream[(String, Int)] = {

    val userClickTimes = originData.map(r => {
      (s"${Constants.FIELD_DATE}=${r._1}|${Constants.FIELD_USERID}=${r._4}|" +
        s"${Constants.FIELD_ADID}=${r._5}", 1)
    })

    def updateFunc(iterator: Iterator[(String, Seq[Int], Option[Int])]): Iterator[(String, Int)] = {
      iterator.flatMap { case (x, y, z) => Some(y.sum + z.getOrElse(0)).map(i => (x, i)) }
    }

    val counts = userClickTimes.updateStateByKey(updateFunc _,
      new HashPartitioner(streamingContext.sparkContext.defaultParallelism),
      rememberPartitioner = true)

    UpdateDateBase.updateUserClickTimes(counts)

    counts
  }


  /**
    * 计算广告在每天每城市被点击次数
    * (date=2017-03-16|province=Henan|city=Zhengzhou|adId=5,5)
    *
    * @param streamingContext
    * @param originData
    * @return
    */
  def countAdClickedTimes(streamingContext: StreamingContext,
                          originData: DStream[(String, String, String, String, String)]
                         ): DStream[(String, Int)] = {

    val filteredDate = getFilteredOriginData(originData)

    val adClickedTimes = filteredDate.map(r => {
      (s"${Constants.FIELD_DATE}=${r._1}|${Constants.FIELD_PROVINCE}=${r._2}|" +
        s"${Constants.FIELD_CITY}=${r._3}|${Constants.FIELD_ADID}=${r._5}", 1)
    })

    def updateFunc(iterator: Iterator[(String, Seq[Int], Option[Int])]): Iterator[(String, Int)] = {
      iterator.flatMap { case (x, y, z) => Some(y.sum + z.getOrElse(0)).map(i => (x, i)) }
    }

    val counts = adClickedTimes.updateStateByKey(updateFunc _,
      new HashPartitioner(streamingContext.sparkContext.defaultParallelism),
      rememberPartitioner = true)

    UpdateDateBase.updateADClickedTimes(counts)

    counts

  }


  /**
    * 获取黑名单：用户每天点击某个广告次数超过100次
    *
    * @param ds
    * @return
    */
  def getBlackList(ds: DStream[(String, Int)]): DStream[(String, Int)] = {

    val blackList = ds.filter(_._2 > Constants.CLICK_TIMES)

    UpdateDateBase.updateBlackList(blackList)

    blackList

  }


  /**
    * 从数据库中查询黑名单
    *
    * @return
    */
  def getBlackListFromDataBase: ListBuffer[String] = {
    val blacklistDaoImplement = DaoFactory.getBlacklistDao
    val blacklists = blacklistDaoImplement.getTable(new JSONObject("{}"))
      .asInstanceOf[Array[Blacklist]]
    val blackListBuffer = new ListBuffer[String]
    for (blacklist <- blacklists) blackListBuffer += blacklist.getUser_id
    blackListBuffer
  }


  /**
    * 获取过滤黑名单后的数据
    *
    * @param originData
    * @return
    */
  def getFilteredOriginData(originData: DStream[(String, String, String, String, String)]
                     ): DStream[(String, String, String, String, String)] = {

    originData.filter(t => {
      !Main.blackList.contains(t._4.trim)
    })
  }

  /**
    * 将获取的用户点击数据过滤黑名单
    *
    * @param dStream
    * @return
    */
  def getFilteredData(dStream: DStream[(String,Int)]):DStream[(String,Int)] = {

    dStream.filter(t => {
      val userId = StringUtils.getFieldFromConcatString(t._1,"\\|",s"${Constants.FIELD_USERID}").trim
      !Main.blackList.contains(userId)
    })

  }

  /**
    * 获取各省广告点击前三
    * @return
    */
  def getTop3AD: Array[ProvinceTop3Ad] = {
    val timeStamp = new Date().getTime
    val date = DateUtils.getDate(timeStamp)
    val daoImplement = DaoFactory.getProvinceClickDao.asInstanceOf[ProvinceClickDaoImplement]
    val json = new JSONObject(s"{'${Constants.FIELD_CLICK_DAY}':'$date'}")
    daoImplement.getTop3Ad(json)
  }

  /**
    * 获取前一小时广告各分钟点击量
    * @return
    */
  def getClickTrend: Array[Ad] ={
    val timeStamp = new Date().getTime
    val date = DateUtils.getDate(timeStamp)
    val timeNow = DateUtils.getTime(timeStamp)
    val timeBefore = DateUtils.getTime(timeStamp-3600000)
    val jsonObject = new JSONObject(s"{'${Constants.FIELD_START_CLICK_DAY}':'$date'," +
      s"'${Constants.FIELD_END_CLICK_DAY}':'$date'," +
      s"'${Constants.FIELD_START_CLICK_TIME}':'$timeBefore'," +
      s"'${Constants.FIELD_END_CLICK_TIME}':'$timeNow'}")
    val adDaoImplement = DaoFactory.getAdDao.asInstanceOf[AdDaoImplement]
    adDaoImplement.getOneHourAdClick(jsonObject)
  }

  /**
    * 通过窗口函数获取前一小时广告各分钟点击量
    * @param originData
    * @return
    */
  def getClickTrendByWindow(originData:DStream[(String, String, String, String, String)]): DStream[(String,Int)] ={
    val filteredData = getFilteredOriginData(originData)
    filteredData.map(r => {
      val time = DateUtils.getMinute(new Date().getTime)
      (s"time=$time|adId=${r._5}",1)
    }).reduceByKeyAndWindow(_+_,Minutes(60))
  }

}
