package cn.cs.scu;

import cn.cs.scu.dao.DaoImplement;
import cn.cs.scu.dao.factory.DaoFactory;
import cn.cs.scu.dao.implement.AdDaoImplement;
import cn.cs.scu.domain.Ad;
import org.json.JSONObject;

/**
 * Created by Wanghan on 2017/3/18.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class testGetOneHourAdClick {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject("{\"start_click_day\":\"2017-3-18\",\"end_click_day\":\"2017-3-18\",\"start_click_time\":\"12:00:00\",\"end_click_time\":\"13:00:00\"}");
        //System.out.println(jsonObject);
        AdDaoImplement adDaoImplement = (AdDaoImplement) DaoFactory.getAdDao();
        Ad[] ads = adDaoImplement.getOneHourAdClick(jsonObject);
        for (Ad ad : ads) {
            System.out.println(ad.getAdId() + "\t" + ad.getClickDay() + "\t" + ad.getClickTime() + "\t" + ad.getClickNumber());
        }
    }
}
