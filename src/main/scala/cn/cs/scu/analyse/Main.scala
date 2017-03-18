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

  var blackList:ListBuffer[String] = RealTimeAnalyse.getBlackListFromDataBase

  def main(args: Array[String]): Unit = {

    val init: (SparkContext, SQLContext, StreamingContext) = InitUnits.initSparkContext()
    val ssc: StreamingContext = init._3

    ssc.checkpoint("/Users/zhangchi/temp")

    val data = MyKafkaUtils.createStream(ssc, "localhost", "g1", "tttt")

    val originData = RealTimeAnalyse.getOriginData(ssc, data)

    val filteredData = RealTimeAnalyse.getFilteredData(originData)

    val userClickTimes = RealTimeAnalyse.countUserClickTimes(ssc, filteredData)

    var newBlackList = RealTimeAnalyse.getBlackList(userClickTimes)

    val adClickedTimes = RealTimeAnalyse.countAdClickedTimes(ssc, filteredData)

    val threadPool: ExecutorService = Executors.newFixedThreadPool(1)

    try {
      threadPool.execute(new UpdateBlackListThread)
    }
    finally {
      threadPool.shutdown()
    }

    userClickTimes.print()
    adClickedTimes.print()
    ssc.start()
    ssc.awaitTermination()

  }
}