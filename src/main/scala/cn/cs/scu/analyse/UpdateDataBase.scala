package cn.cs.scu.analyse

import org.apache.spark.streaming.dstream.DStream

/**
  * Created by zhangchi on 17/3/16.
  */
object UpdateDataBase {

  def updateBlackList(blackList:DStream[String]): Unit ={

  }

  def updateUserClickTimes(userClickTimes:DStream[(String, Int)]): Unit ={

  }

  def updateADClickedTimes(adClickedTimes:DStream[(String, Int)]): Unit ={

  }

}
