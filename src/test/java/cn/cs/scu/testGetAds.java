package cn.cs.scu;

import cn.cs.scu.dao.DaoImplement;
import cn.cs.scu.dao.factory.DaoFactory;
import cn.cs.scu.domain.Ad;
import org.json.JSONObject;

/**
 * Created by Wanghan on 2017/3/17.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class testGetAds {
    public static void main (String[] args){
        DaoImplement daoImplement = DaoFactory.getAdDao();
        JSONObject json = new JSONObject("{\"ad_type\":\"study\"}");
        Ad[] ads = (Ad[])daoImplement.getTable(json);
        for (Ad ad :ads){
            System.out.println(ad.getAdId());
        }
    }
}
