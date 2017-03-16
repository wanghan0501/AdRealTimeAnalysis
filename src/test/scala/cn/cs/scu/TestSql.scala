package cn.cs.scu

import cn.cs.scu.dao.factory.DaoFactory
import cn.cs.scu.domain.Blacklist
import org.json.JSONObject

import scala.collection.mutable.ListBuffer

/**
  * Created by zhangchi on 17/3/16.
  */
object TestSql extends App{
  val blacklistDaoImplement = DaoFactory.getBlacklistDao
  val blacklists = blacklistDaoImplement.getTable(new JSONObject("{}")).asInstanceOf[Array[Blacklist]]
  val blackListBuffer = new ListBuffer[String]
  for (blacklist <- blacklists) blackListBuffer += blacklist.getUser_name

  println(blackListBuffer)
}
