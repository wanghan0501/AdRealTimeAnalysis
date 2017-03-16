package cn.cs.scu.analyse

import cn.cs.scu.dao.factory.DaoFactory
import cn.cs.scu.domain.Blacklist
import cn.cs.scu.scalautils.DateUtils
import org.apache.spark.HashPartitioner
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.json.JSONObject

import scala.collection.mutable.ListBuffer

/**
  * Created by zhangchi on 17/3/15.
  */
object RealTimeAnalyse {

    /**
      * 返回原始数据
      * @param streamingContext
      * @param data
      * @return
      */
    def getOriginData(streamingContext: StreamingContext,
                      data: ReceiverInputDStream[(String, String)]
                     ): DStream[(String,String,String,String,String)] = {

        val logs = data.map(_._2).flatMap(_.split("\n")).map(row => {
            row.split("\t").toBuffer
        })

        logs.map(r => {
            (DateUtils.getDate(r.head.toLong),r(1),r(2),r(3),r(4))
        })

    }


    /**
      * 计算用户点击广告次数
      *(date=2017-03-16|userId=487|adId=9,1)
      * @param streamingContext
      * @param filteredData
      * @return
      */
    def countUserClickTimes(streamingContext: StreamingContext,
                            filteredData: DStream[(String,String,String,String,String)]): DStream[(String, Int)] = {

        val userClickTimes = filteredData.map(r => {
            (s"date=${r._1}|userId=${r._4}|adId=${r._5}", 1)
        })

        def updateFunc(iterator: Iterator[(String, Seq[Int], Option[Int])]): Iterator[(String, Int)] = {
            iterator.flatMap { case (x, y, z) => Some(y.sum + z.getOrElse(0)).map(i => (x, i)) }
        }

        val counts = userClickTimes.updateStateByKey(updateFunc _,
            new HashPartitioner(streamingContext.sparkContext.defaultParallelism), rememberPartitioner = true)

        counts
    }


    /**
      * 计算广告在每天每城市被点击次数
      * (date=2017-03-16|province=Henan|city=Zhengzhou|adId=5,5)
      * @param streamingContext
      * @param filteredData
      * @return
      */
    def countAdClickedTimes(streamingContext:StreamingContext,
                            filteredData: DStream[(String,String,String,String,String)]): DStream[(String, Int)] ={


        val adClickedTimes = filteredData.map(r => {
            (s"date=${r._1}|province=${r._2}|city=${r._3}|adId=${r._5}", 1)
        })

        def updateFunc(iterator: Iterator[(String, Seq[Int], Option[Int])]): Iterator[(String, Int)] = {
            iterator.flatMap { case (x, y, z) => Some(y.sum + z.getOrElse(0)).map(i => (x, i)) }
        }

        val counts = adClickedTimes.updateStateByKey(updateFunc _,
            new HashPartitioner(streamingContext.sparkContext.defaultParallelism), rememberPartitioner = true)

        counts

    }


    /**
      * 获取黑名单：用户每天点击某个广告次数超过100次
      *
      * @param ds
      * @return
      */
    def getBlackList(ds: DStream[(String, Int)]): DStream[(String)] = {
        ds.filter(_._2 > 100).map(_._1)
    }


    /**
      * 从数据库中查询黑名单
      * @return
      */
    def getBlackListFromDataBase: ListBuffer[String] ={
        val blacklistDaoImplement = DaoFactory.getBlacklistDao
        val blacklists = blacklistDaoImplement.getTable(new JSONObject("{}")).asInstanceOf[Array[Blacklist]]
        val blackListBuffer = new ListBuffer[String]
        for (blacklist <- blacklists) blackListBuffer += blacklist.getUser_name
        blackListBuffer
    }


    /**
      * 获取过滤黑名单后的数据
      * @param originData
      * @return
      */
    def getFilteredData(originData:DStream[(String,String,String,String,String)]): DStream[(String,String,String,String,String)] = {

        val blackList = getBlackListFromDataBase
        originData.filter(t => {
            !blackList.contains(t._4)
        })
    }
}
