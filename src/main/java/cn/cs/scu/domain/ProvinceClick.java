package cn.cs.scu.domain;

import java.io.Serializable;

/**
 * 各个省份各个城市点击广告类
 * <p>
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class ProvinceClick implements Serializable {

    //初始化各参数
    private String province = "";
    private String city = "";
    private String adId = "";
    private String clickDay = "";
    private Long clickNum = 0L;

    /**
     * 获取省份名称
     * @return
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省份
     * @param province 省份名称
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取城市
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置城市
     * @param city 城市名称
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取广告id
     * @return
     */
    public String getAdId() {
        return adId;
    }

    /**
     * 设置广告id
     * @param adId 广告id
     */
    public void setAdId(String adId) {
        this.adId = adId;
    }

    /**
     * 获取点击日期
     * @return
     */
    public String getClickDay() {
        return clickDay;
    }

    /**
     * 设置点击日期
     * @param clickDay 点击日期
     */
    public void setClickDay(String clickDay) {
        this.clickDay = clickDay;
    }

    /**
     * 获取点击次数
     * @return
     */
    public Long getClickNum() {
        return clickNum;
    }

    /**
     * 设置点击次数
     * @param clickNum 点击次数
     */
    public void setClickNum(Long clickNum) {
        this.clickNum = clickNum;
    }
}
