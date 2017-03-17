package cn.cs.scu

import cn.cs.scu.dao.DaoImplement
import cn.cs.scu.dao.factory.DaoFactory
import cn.cs.scu.domain.Blacklist


/**
  * Created by zhangchi on 17/3/16.
  */
object TestUpdateBL extends App{
  val blacklist1 = new Blacklist
  blacklist1.setUser_id("7")
  blacklist1.setUser_name("asd")
  val blacklist2 = new Blacklist
  blacklist2.setUser_id("8")
  blacklist2.setUser_name("xdv")
  val arrayList =  Array[Blacklist](blacklist1,blacklist2)
  val blacklistDaoImplement: DaoImplement = DaoFactory.getBlacklistDao
  blacklistDaoImplement.updateTable(arrayList.asInstanceOf[Array[AnyRef]])


}
