package cn.cs.scu.dao;

import org.json.JSONObject;


/**
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */

/**
 * 工厂类实现
 * 更新表
 * 获取表中参数
 */
public abstract class DaoImplement {
    public abstract void updateTable(Object[] object);

    public abstract Object[] getTable(JSONObject param);
}
