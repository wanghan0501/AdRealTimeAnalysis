package cn.cs.scu;

import cn.cs.scu.dao.DaoImplement;
import cn.cs.scu.dao.factory.DaoFactory;
import cn.cs.scu.dao.implement.ProvinceClickDaoImplement;
import cn.cs.scu.domain.ProvinceTop3Ad;
import org.json.JSONObject;

/**
 * Created by Wanghan on 2017/3/18.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class testGetTopAd {
    public static void main (String[] args){
        ProvinceClickDaoImplement daoImplement =(ProvinceClickDaoImplement) DaoFactory.getProvinceClickDao();
        JSONObject json = new JSONObject("{\"click_day\":\"2017-03-17\"}");
        ProvinceTop3Ad[] provinceTop3Ads= daoImplement.getTop3Ad(json);

        for (ProvinceTop3Ad provinceTop3Ad :provinceTop3Ads){
            System.out.println(provinceTop3Ad.toString());
        }
    }
}
