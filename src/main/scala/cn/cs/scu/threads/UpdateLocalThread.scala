package cn.cs.scu.threads

import cn.cs.scu.analyse.{Main, RealTimeAnalyse}

/**
  * Created by zhangchi on 17/3/17.
  */
class UpdateLocalThread extends Runnable{
  override def run(): Unit = {
    while (true){
      Main.blackList = RealTimeAnalyse.getBlackListFromDataBase
//      Main.newBlackList = RealTimeAnalyse.getBlackList(Main.userClickTimes)
      println(Main.blackList)
      Thread.sleep(5000)
    }
  }
}
