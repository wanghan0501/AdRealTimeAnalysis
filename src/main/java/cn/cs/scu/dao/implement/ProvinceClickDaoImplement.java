package cn.cs.scu.dao.implement;

import cn.cs.scu.constants.Constants;
import cn.cs.scu.dao.DaoImplement;
import cn.cs.scu.domain.ProvinceClick;
import cn.cs.scu.domain.UserClick;
import cn.cs.scu.jdbc.JDBCHelper;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * 省份广告点击数据访问对象实现类
 * <p>
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class ProvinceClickDaoImplement extends DaoImplement {
    @Override
    public void updateTable(Object[] provinceClicks) {
        if (provinceClicks instanceof ProvinceClick[]) {
            // jdbc单例
            JDBCHelper jdbcHelper = JDBCHelper.getInstanse();
            String sql = "INSERT INTO " + Constants.TABLE_PROVINCE_CLICK + "(" + Constants.FIELD_PROVINCE + ","
                    + Constants.FIELD_CITY + "," + Constants.FIELD_AD_ID + "," + Constants.FIELD_CLICK_DAY + "," +
                    Constants.FIELD_CLICK_NUMBER + ") VALUE(?,?,?,?,?) ON DUPLICATE KEY UPDATE " + Constants.FIELD_CLICK_NUMBER +
                    "=?";
            System.out.println(sql);
            jdbcHelper.excuteInsert(sql, provinceClicks, new JDBCHelper.InsertCallback() {
                @Override
                public void process(String sql, PreparedStatement preparedStatement, Object[] objects) throws Exception {
                    for (ProvinceClick provinceClick : (ProvinceClick[]) provinceClicks) {
                        preparedStatement.setObject(1, provinceClick.getProvince());
                        preparedStatement.setObject(2, provinceClick.getCity());
                        preparedStatement.setObject(3, provinceClick.getAdId());
                        preparedStatement.setObject(4, provinceClick.getClickDay());
                        preparedStatement.setObject(5, provinceClick.getClickNum());
                        preparedStatement.setObject(6, provinceClick.getClickNum());
                        preparedStatement.addBatch();
                    }
                    // 批量插入
                    preparedStatement.executeBatch();
                }
            });
        }
    }

    @Override
    public Object[] getTable(JSONObject param) {
        return new Object[0];
    }
}
