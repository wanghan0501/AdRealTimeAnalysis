package cn.cs.scu.analyse


import java.util.concurrent.{ExecutorService, Executors}

import cn.cs.scu.scalautils.{InitUnits, MyKafkaUtils}
import cn.cs.scu.threads.UpdateBlackListThread
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.StreamingContext

import scala.collection.mutable.ListBuffer

/**
  * Created by zhangchi on 17/3/16.
  */


object Main {

  var blackList:ListBuffer[String] = new ListBuffer[String]

  def main(args: Array[String]): Unit = {

    val init: (SparkContext, SQLContext, StreamingContext) = InitUnits.initSparkContext()
    val ssc: StreamingContext = init._3

    ssc.checkpoint("/Users/zhangchi/temp")

    val data = MyKafkaUtils.createStream(ssc, "localhost", "g1", "tttt")

    //获取原始数据
    val originData = RealTimeAnalyse.getOriginData(ssc, data)
    //获取用户点击数据
    val userClickTimes = RealTimeAnalyse.countUserClickTimes(ssc, originData)
    //过滤用户点击数据
    val filteredUserClickTimes = RealTimeAnalyse.getFilteredData(userClickTimes)
    //获取新增黑名单
    var newBlackList = RealTimeAnalyse.getBlackList(filteredUserClickTimes)
    //获取广告被点击数据
    val adClickedTimes = RealTimeAnalyse.countAdClickedTimes(ssc, originData)


    //创建更新黑名单线程
    val threadPool: ExecutorService = Executors.newFixedThreadPool(1)
    try {
      threadPool.execute(new UpdateBlackListThread)
    }
    finally {
      threadPool.shutdown()
    }

    //查询各省广告点击前三
//    val provinceTop3Ads = RealTimeAnalyse.getTop3AD
//    for (provinceTop3Ad <- provinceTop3Ads) {
//      println(provinceTop3Ad.toString)
//    }

    filteredUserClickTimes.print()
    adClickedTimes.print()
    ssc.start()
    ssc.awaitTermination()

  }
}