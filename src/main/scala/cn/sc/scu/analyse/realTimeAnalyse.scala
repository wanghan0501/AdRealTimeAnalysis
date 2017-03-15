package cn.sc.scu.analyse

import org.apache.spark.HashPartitioner
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

/**
  * Created by zhangchi on 17/3/15.
  */
object realTimeAnalyse {

  /**
    * 计算用户点击广告次数
    * 返回格式 <"time_userid_adid",times>
    * @param streamingContext
    * @param data
    * @return
    */
  def countClickTimes(streamingContext:StreamingContext, data:ReceiverInputDStream[(String, String)]): DStream[(String, Int)] ={

    val logs = data.map(_._2).flatMap(_.split("\n")).map(row => {
      val array = row.split("\t").toBuffer
      array
    })

    val advertisements = logs.map(r =>{
      (s"${r.head}_${r(3)}_${r(4)}",1)
    })

    def updateFunc(iterator: Iterator[(String, Seq[Int], Option[Int])]): Iterator[(String, Int)] = {
      iterator.flatMap { case (x, y, z) => Some(y.sum + z.getOrElse(0)).map(i => (x, i)) }
    }

    val adCounts = advertisements.updateStateByKey(updateFunc _,
      new HashPartitioner(streamingContext.sparkContext.defaultParallelism), rememberPartitioner = true)

    adCounts
  }

  /**
    * 获取黑名单：用户每天点击某个广告次数超过100次
    * @param ds
    * @return
    */
  def getBlackList(ds:DStream[(String, Int)]): DStream[(String, Int)] ={
    ds.filter(_._2>100)
  }

  /**
    * 过滤黑名单用户
    * @param ds
    * @param blackList
    * @return
    */
  def clickTimesFilter(ds:DStream[(String, Int)],blackList:List[String]): DStream[(String, Int)] ={
    ds.filter(row => !blackList.contains(row._1))
  }

}
