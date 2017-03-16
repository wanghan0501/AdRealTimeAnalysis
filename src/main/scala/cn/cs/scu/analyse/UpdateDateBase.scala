package cn.cs.scu.analyse

import java.util.concurrent.Callable

import org.apache.spark.streaming.dstream.DStream

import scala.collection.mutable.ListBuffer

/**
  * Created by zhangchi on 17/3/16.
  */
class UpdateDateBase(var blackList: ListBuffer[String],
                     val newBlackList: DStream[String],
                     val userClickTimes: DStream[(String, Int)],
                     val adClickedTimes: DStream[(String, Int)]
                    ) extends Callable[ListBuffer[String]] {

  /**
    * 更新数据库黑名单
    *
    * @param blackList
    */
  def updateBlackList(blackList: DStream[String]): Unit = {
    blackList.foreachRDD(rdd => {
      rdd.foreach(s => {
        /**
          * 更新每条黑名单用户
          */
      })
    })
  }

  /**
    * 更新数据库用户点击广告数
    *
    * @param userClickTimes
    */
  def updateUserClickTimes(userClickTimes: DStream[(String, Int)]): Unit = {

  }

  /**
    * 更新数据库广告被点击数
    *
    * @param adClickedTimes
    */
  def updateADClickedTimes(adClickedTimes: DStream[(String, Int)]): Unit = {

  }

  /**
    * 复写call方法，返回更新过的黑名单
    *
    * @return
    */
  override def call(): ListBuffer[String] = {
    Thread.sleep(5000)

    updateBlackList(newBlackList)
    updateUserClickTimes(userClickTimes)
    updateADClickedTimes(adClickedTimes)

    RealTimeAnalyse.getBlackListFromDataBase
  }

}
