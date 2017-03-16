package cn.cs.scu

import cn.cs.scu.dao.factory.DaoFactory
import cn.cs.scu.domain.ProvinceClick

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Wanghan on 2017/3/16.
  * Copyright © Wanghan SCU. All Rights Reserved
  */
object testUpdateProvinceClick extends App{
    val p1 = new ProvinceClick
    p1.setProvince("s1")
    p1.setCity("s2")
    p1.setAdId("1212")
    p1.setClickDay("2017-03-05")
    p1.setClickNum(10L)

    val arraylist = new ArrayBuffer[ProvinceClick]()

    arraylist+=p1
    val p = DaoFactory.getProvinceClickDao
    p.updateTable(arraylist.toArray.asInstanceOf[Array[AnyRef]])
}
