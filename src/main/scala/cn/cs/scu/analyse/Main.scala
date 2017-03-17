package cn.cs.scu.analyse


import java.util.concurrent.{ExecutorService, Executors}

import cn.cs.scu.scalautils.{InitUnits, MyKafkaUtils}
import cn.cs.scu.threads.UpdateLocalThread
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

import scala.collection.mutable.ListBuffer

/**
  * Created by zhangchi on 17/3/16.
  */


object Main {

  val init: (SparkContext, SQLContext, StreamingContext) = InitUnits.initSparkContext()
  val ssc: StreamingContext = init._3

  ssc.checkpoint("/Users/zhangchi/temp")

  var blackList:ListBuffer[String] = RealTimeAnalyse.getBlackListFromDataBase

  val data: ReceiverInputDStream[(String, String)] =
    MyKafkaUtils.createStream(ssc, "localhost", "g1", "tttt")

  val originData: DStream[(String, String, String, String, String)] =
    RealTimeAnalyse.getOriginData(ssc, data)

  val filteredData: DStream[(String, String, String, String, String)] =
    RealTimeAnalyse.getFilteredData(originData)

  val userClickTimes: DStream[(String, Int)] = RealTimeAnalyse.countUserClickTimes(ssc, filteredData)

  var newBlackList: DStream[String] = RealTimeAnalyse.getBlackList(userClickTimes)

  val adClickedTimes: DStream[(String, Int)] = RealTimeAnalyse.countAdClickedTimes(ssc, filteredData)

  def main(args: Array[String]): Unit = {

    val threadPool: ExecutorService = Executors.newFixedThreadPool(2)
    try {
      threadPool.execute(new UpdateLocalThread)
    }
    finally {
      threadPool.shutdown()
    }

    Main.userClickTimes.print()
    Main.ssc.start()
    Main.ssc.awaitTermination()

  }
}