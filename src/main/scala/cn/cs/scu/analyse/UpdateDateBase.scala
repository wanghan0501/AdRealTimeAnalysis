package cn.cs.scu.analyse

import java.util.Date

import cn.cs.scu.dao.DaoImplement
import cn.cs.scu.dao.factory.DaoFactory
import cn.cs.scu.domain.{Ad, Blacklist, ProvinceClick, UserClick}
import cn.cs.scu.javautils.StringUtils
import cn.cs.scu.scalautils.DateUtils
import org.apache.spark.streaming.dstream.DStream


/**
  * Created by zhangchi on 17/3/16.
  */
object UpdateDateBase {
  /**
    * 更新数据库黑名单
    *
    * @param blackList
    */
  def updateBlackList(blackList: DStream[(String,Int)]): Unit = {
    blackList.foreachRDD(rdd => {
      val list = rdd.map(r => {
        val blackList = new Blacklist
        blackList.setUser_id(StringUtils.getFieldFromConcatString(r._1,"\\|","userId"))
        blackList
      }).collect().toList

      val dao = DaoFactory.getBlacklistDao
      dao.updateTable(list.toArray.asInstanceOf[Array[AnyRef]])

    })
  }

  /**
    * 更新数据库用户点击广告数
    *
    * @param userClickTimes
    */
  def updateUserClickTimes(userClickTimes: DStream[(String, Int)]): Unit = {
    userClickTimes.foreachRDD(rdd => {
      val list = rdd.map(r => {
        val userClick = new UserClick()
        userClick.setAdId(StringUtils.getFieldFromConcatString(r._1,"\\|","adId").toLong)
        userClick.setClickDay(StringUtils.getFieldFromConcatString(r._1,"\\|","date"))
        userClick.setUserId(StringUtils.getFieldFromConcatString(r._1,"\\|","userId").toLong)
        userClick.setClickNum(r._2.toLong)
        userClick
      }).collect().toList

      val dao = DaoFactory.getUserClickDao
      dao.updateTable(list.toArray.asInstanceOf[Array[AnyRef]])

    })
  }

  /**
    * 更新数据库广告被点击数
    *
    * @param adClickedTimes
    */
  def updateADClickedTimes(adClickedTimes: DStream[(String, Int)]): Unit = {
    adClickedTimes.foreachRDD(rdd => {
      val list1 = rdd.map(r => {
        val provinceClick = new ProvinceClick
        provinceClick.setProvince(StringUtils.getFieldFromConcatString(r._1,"\\|","province"))
        provinceClick.setCity(StringUtils.getFieldFromConcatString(r._1,"\\|","city"))
        provinceClick.setAdId(StringUtils.getFieldFromConcatString(r._1,"\\|","adId"))
        provinceClick.setClickDay(StringUtils.getFieldFromConcatString(r._1,"\\|","date"))
        provinceClick.setClickNum(r._2.toLong)
        provinceClick
      }).collect().toList

      val list2 = rdd.map(r => {
        val ad = new Ad()
        ad.setAdId(StringUtils.getFieldFromConcatString(r._1,"\\|","adId").toLong)
        ad.setClickDay(StringUtils.getFieldFromConcatString(r._1,"\\|","date"))
        ad.setClickTime(DateUtils.getTime(new Date().getTime))
        ad.setClickNumber(r._2.toLong)
        ad
      }).collect().toList

      val dao1 = DaoFactory.getProvinceClickDao
      dao1.updateTable(list1.toArray.asInstanceOf[Array[AnyRef]])

      val dao2 = DaoFactory.getAdDao
      dao2.updateTable(list2.toArray.asInstanceOf[Array[AnyRef]])

    })

  }
}
