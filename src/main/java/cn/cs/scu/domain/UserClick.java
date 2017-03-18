package cn.cs.scu.domain;

import java.io.Serializable;

/**
 * 用户点击类
 * <p>
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class UserClick implements Serializable {

    //初始化参数
    private Long userId = 0L;
    private Long adId = 0L;
    private String clickDay = "";
    private Long clickNum = 0L;

    /**
     * 获取用户id
     * @return
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取广告id
     * @return
     */
    public Long getAdId() {
        return adId;
    }

    /**
     * 设置广告id
     * @param adId 广告id
     */
    public void setAdId(Long adId) {
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
     * @param clickDay
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
