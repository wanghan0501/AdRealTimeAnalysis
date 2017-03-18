package cn.cs.scu

import cn.cs.scu.analyse.{RealTimeAnalyse, UpdateDateBase}
import cn.cs.scu.domain.ProvinceTop3Ad
import cn.cs.scu.javautils.StringUtils
import cn.cs.scu.scalautils.{InitUnits, MyKafkaUtils}

/**
  * Created by zhangchi on 17/3/15.
  */
object Test extends App{

  val provinceTop3Ads = RealTimeAnalyse.getTop3AD
  for (provinceTop3Ad <- provinceTop3Ads) {
    println(provinceTop3Ad.toString)
  }
}
