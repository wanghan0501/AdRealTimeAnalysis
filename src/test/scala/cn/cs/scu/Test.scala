package cn.cs.scu

import cn.cs.scu.analyse.{RealTimeAnalyse, UpdateDateBase}
import cn.cs.scu.domain.ProvinceTop3Ad
import cn.cs.scu.javautils.StringUtils
import cn.cs.scu.scalautils.{InitUnits, MyKafkaUtils}

/**
  * Created by zhangchi on 17/3/15.
  */
object Test extends App{

  val ads = RealTimeAnalyse.getClickTrend
  for (ad <- ads) {
    println(ad.getAdId + "\t" + ad.getClickDay + "\t" + ad.getClickTime + "\t" + ad.getClickNumber)
  }

}
