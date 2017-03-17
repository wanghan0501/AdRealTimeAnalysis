package cn.cs.scu

import cn.cs.scu.analyse.RealTimeAnalyse
import cn.cs.scu.javautils.StringUtils
import cn.cs.scu.scalautils.{InitUnits, MyKafkaUtils}

/**
  * Created by zhangchi on 17/3/15.
  */
object Test extends App{

  val str = "date=2017-03-17|userId=509|adId=2"

  val userid = StringUtils.getFieldFromConcatString(str,"\\|","userId")
  println(userid)

}
