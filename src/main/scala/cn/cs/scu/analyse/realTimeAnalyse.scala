package cn.cs.scu.analyse

import cn.cs.scu.scalautils.DateUtils
import org.apache.spark.HashPartitioner
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

/**
  * Created by zhangchi on 17/3/15.
  */
object realTimeAnalyse {

    def getOriginData(streamingContext: StreamingContext,
                      data: ReceiverInputDStream[(String, String)]): DStream[(String,String,String,String,String)] = {

        val logs = data.map(_._2).flatMap(_.split("\n")).map(row => {
            row.split("\t").toBuffer
        })

        logs.map(r => {
            val date = new DateUtils(r.head.toLong)
            (date.getDate,r(1),r(2),r(3),r(4))
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
      * 过滤黑名单用户
      *
      * @param originData
      * @param blackList
      * @return
      */
    def filterBlackList(originData: DStream[(String,String,String,String,String)],
                        blackList: Array[String]): DStream[(String,String,String,String,String)] = {
        originData.filter(t => {
            !blackList.contains(t._4)
        })
    }
}
