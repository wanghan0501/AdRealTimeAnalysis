package cn.cs.scu

import cn.cs.scu.dao.factory.DaoFactory
import cn.cs.scu.domain.Ad

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Wanghan on 2017/3/16.
  * Copyright © Wanghan SCU. All Rights Reserved
  */
object testUpdateAd extends App{
    val ad1 = new Ad()
    ad1.setAdId(1L)
    ad1.setClickDay("2017-03-05")
    ad1.setClickTime("23:05:12")
    ad1.setClickNumber(6L)
    val array1 = new ArrayBuffer[Ad]()
    array1+=ad1
    val adDaoImplement = DaoFactory.getAdDao
    adDaoImplement.updateTable(array1.toArray.asInstanceOf[Array[AnyRef]])

}
