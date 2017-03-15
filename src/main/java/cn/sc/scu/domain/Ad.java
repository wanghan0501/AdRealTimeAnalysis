package cn.sc.scu.domain;

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

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getClickDay() {
        return clickDay;
    }

    public void setClickDay(String clickDay) {
        this.clickDay = clickDay;
    }

    public String getClickTime() {
        return clickTime;
    }

    public void setClickNumber(Long clickNumber) {
        this.clickNumber = clickNumber;
    }

    public Long getClickNumber() {
        return clickNumber;
    }

    public void setClickTime(String clickTime) {
        this.clickTime = clickTime;
    }
}
