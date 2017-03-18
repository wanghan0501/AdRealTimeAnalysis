package cn.cs.scu.domain;

import java.io.Serializable;

/**
 * 广告类
 * <p>
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class Ad implements Serializable {
    private Long adId = 0L;
    private String adType = "";
    private String clickDay = "";
    private String clickTime = "";
    private Long clickNumber = 0L;

    /**
     * 获取广告id
     * @return
     */
    public Long getAdId() {
        return adId;
    }

    /**
     * 设置广告id
     * @param adId
     */
    public void setAdId(Long adId) {
        this.adId = adId;
    }

    /**
     * 获取广告类型
     * @return
     */
    public String getAdType() {
        return adType;
    }

    /**
     * 设置广告类型
     * @param adType
     */
    public void setAdType(String adType) {
        this.adType = adType;
    }

    /**
     * 获取每次点击的日期
     * @return
     */
    public String getClickDay() {
        return clickDay;
    }

    /**
     * 设置每次广告点击的日期
     * @param clickDay
     */
    public void setClickDay(String clickDay) {
        this.clickDay = clickDay;
    }

    /**
     * 获取点击的时间
     * @return
     */
    public String getClickTime() {
        return clickTime;
    }

    /**
     * 设置点击的时间
     * @param clickTime
     */
    public void setClickTime(String clickTime) {
        this.clickTime = clickTime;
    }

    /**
     * 设置广告点击次数
     * @param clickNumber
     */
    public void setClickNumber(Long clickNumber) {
        this.clickNumber = clickNumber;
    }

    /**
     * 获取广告点击次数
     * @return Long
     */
    public Long getClickNumber() {
        return clickNumber;
    }


}
