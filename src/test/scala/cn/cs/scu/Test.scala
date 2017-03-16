package cn.cs.scu

import cn.cs.scu.analyse.RealTimeAnalyse
import cn.cs.scu.scalautils.{InitUnits, MyKafkaUtils}

/**
  * Created by zhangchi on 17/3/15.
  */
object Test extends App{

  val init = InitUnits.initSparkContext()
  val ssc = init._3

  ssc.checkpoint("/Users/zhangchi/temp")

  val zkQuorum = "localhost"
  val group = "g1"
  val topics = "tttt"

  val data = MyKafkaUtils.createStream(ssc,zkQuorum,group,topics)

  val originData = RealTimeAnalyse.getOriginData(ssc,data)

  val blackList = RealTimeAnalyse.getBlackListFromDataBase

  val filteredData = RealTimeAnalyse.getFilteredData(originData)

  val userClickTimes = RealTimeAnalyse.countUserClickTimes(ssc,filteredData)

  val newBlackList = RealTimeAnalyse.getBlackList(userClickTimes)

//  val adClickedTimes = realTimeAnalyse.countAdClickedTimes(ssc,filteredData)

  userClickTimes.print()
  ssc.start()
  ssc.awaitTermination()

}
