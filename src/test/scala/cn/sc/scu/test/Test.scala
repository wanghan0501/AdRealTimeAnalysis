package cn.sc.scu.test

import cn.sc.scu.analyse.realTimeAnalyse
import cn.sc.scu.scalautils.{InitUnits, MyKafkaUtils}

/**
  * Created by zhangchi on 17/3/15.
  */
object Test extends App{

  val init = InitUnits.initSparkContext()
  val ssc = init._3

  ssc.checkpoint("/Users/zhangchi/temp")

  val zkQuorum = "localhost"
  val group = "g1"
  val topics = "AdRealTimeLog"

  val data = MyKafkaUtils.createStream(ssc,zkQuorum,group,topics)

  val wordCounts = realTimeAnalyse.countClickTimes(ssc,data)


  wordCounts.print()
  ssc.start()
  ssc.awaitTermination()

}
