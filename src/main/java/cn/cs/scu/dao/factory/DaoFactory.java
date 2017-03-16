package cn.cs.scu.dao.factory;

import cn.cs.scu.dao.DaoImplement;
import cn.cs.scu.dao.implement.AdDaoImplement;
import cn.cs.scu.dao.implement.BlacklistDaoImplement;

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

}
