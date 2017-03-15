package cn.sc.scu.analyse

import org.apache.spark.HashPartitioner
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

/**
  * Created by zhangchi on 17/3/15.
  */
object realTimeAnalyse {

  def countClickTimes(streamingContext:StreamingContext, data:ReceiverInputDStream[(String, String)]): DStream[(String, Int)] ={

    val logs = data.map(_._2).flatMap(_.split("\n")).map(row => {
      val array = row.split("\t").toBuffer
      array
    })

    val advertisements = logs.map(r =>{
      (s"${r.head}_${r(3)}_${r(4)}",1)
    })


    def updateFunc(iter: Iterator[(String, Seq[Int], Option[Int])]): Iterator[(String, Int)] = {
      iter.flatMap { case (x, y, z) => Some(y.sum + z.getOrElse(0)).map(i => (x, i)) }
    }

    val adCounts = advertisements.updateStateByKey(updateFunc _,
      new HashPartitioner(streamingContext.sparkContext.defaultParallelism), rememberPartitioner = true)

    adCounts
  }

}
