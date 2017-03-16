package cn.cs.scu.dao.factory;

import cn.cs.scu.dao.DaoImplement;
import cn.cs.scu.dao.implement.*;

/**
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class DaoFactory {

    /**
     * 工厂构造广告数据访问类
     *
     * @return
     */
    public static DaoImplement getAdDao() {
        return new AdDaoImplement();
    }

    /**
     * 工厂构造黑名单数据访问类
     *
     * @return
     */
    public static DaoImplement getBlacklistDao() {
        return new BlacklistDaoImplement();
    }

    /**
     * 工厂构造省份点击数据访问类
     * @return
     */
    public static DaoImplement getProvinceClickDao(){
        return new ProvinceClickDaoImplement();
    }

    /**
     * 工厂构造用户点击数据访问类
     * @return
     */
    public static DaoImplement getUserClickDao(){
        return new UserClickDaoImplement();
    }

    /**
     * 工厂构造用户数据访问类
     * @return
     */
    public static DaoImplement getUserDao(){
        return new UserDaoImplement();
    }

}
