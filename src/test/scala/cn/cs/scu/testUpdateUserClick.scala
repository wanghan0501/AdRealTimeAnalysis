package cn.cs.scu

import cn.cs.scu.dao.factory.DaoFactory
import cn.cs.scu.domain.UserClick

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Wanghan on 2017/3/16.
  * Copyright © Wanghan SCU. All Rights Reserved
  */
object testUpdateUserClick extends App{
    val userclick1 = new UserClick()
    userclick1.setAdId(1L)
    userclick1.setClickDay("2017-03-05")
    userclick1.setUserId(1L)
    userclick1.setClickNum(8L)

    val userclick2 = new UserClick()
    userclick2.setAdId(2L)
    userclick2.setClickDay("2017-03-05")
    userclick2.setUserId(1L)
    userclick2.setClickNum(8L)
    val array1 = new ArrayBuffer[UserClick]()
    array1+=userclick1
    array1+=userclick2
    val a1 = DaoFactory.getUserClickDao
    a1.updateTable(array1.toArray.asInstanceOf[Array[AnyRef]])

}
