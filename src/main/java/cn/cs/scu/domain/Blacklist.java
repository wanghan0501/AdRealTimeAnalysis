package cn.cs.scu.domain;

import java.io.Serializable;

/**
 * 黑名单用户类
 * <p>
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class Blacklist implements Serializable {

    private String user_id = "";
    private String user_name = "";

    /**
     * 获取黑名单用户的id
     * @return
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * 设置黑名单用户id
     * @param user_id 用户id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * 获取黑名单用户姓名
     * @return
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * 设置黑名单用户姓名
     * @param user_name 用户姓名
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
