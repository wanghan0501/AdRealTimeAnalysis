package cn.sc.scu.scalautils

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.kafka.KafkaUtils

/**
  * Created by zhangchi on 17/3/15.
  */
object MyKafkaUtils {

  def createStream(
                    ssc:StreamingContext,
                    zkQuorum:String,
                    group:String,
                    topics:String,
                    numThreads:Int = 2,
                    storageLevel: StorageLevel = StorageLevel.MEMORY_AND_DISK_SER
                  ): ReceiverInputDStream[(String, String)] = {

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap

    KafkaUtils.createStream(ssc,zkQuorum,group,topicMap,storageLevel)
  }

}
