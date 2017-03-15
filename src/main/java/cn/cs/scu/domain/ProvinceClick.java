package cn.cs.scu.domain;

import java.io.Serializable;

/**
 * 各个省份各个城市点击广告类
 * <p>
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class ProvinceClick implements Serializable {

    private String province = "";
    private String city = "";
    private String adId = "";
    private String date = "";
    private Long clickNum = 0L;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getClickNum() {
        return clickNum;
    }

    public void setClickNum(Long clickNum) {
        this.clickNum = clickNum;
    }
}
