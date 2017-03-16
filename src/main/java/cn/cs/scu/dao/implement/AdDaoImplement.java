package cn.cs.scu.dao.implement;

import cn.cs.scu.constants.Constants;
import cn.cs.scu.dao.DaoImplement;
import cn.cs.scu.domain.Ad;
import cn.cs.scu.domain.Blacklist;
import cn.cs.scu.jdbc.JDBCHelper;
import org.json.JSONObject;

import java.sql.PreparedStatement;

/**
 * 广告表数据访问对象实现类
 * <p>
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class AdDaoImplement extends DaoImplement {
    @Override
    public void updateTable(Object[] ads) {
        if (ads instanceof Ad[]) {
            // jdbc单例
            JDBCHelper jdbcHelper = JDBCHelper.getInstanse();
            String sql = "INSERT INTO " + Constants.TABLE_AD + "(" + Constants.FIELD_AD_ID + ","
                    + Constants.FIELD_CLICK_DAY + "," + Constants.FIELD_CLICK_TIME + "," +
                    Constants.FIELD_CLICK_NUMBER + ") VALUE(?,?,?,?) ON DUPLICATE KEY UPDATE " + Constants.FIELD_CLICK_NUMBER +
                    "=?";
            System.out.println(sql);
            jdbcHelper.excuteInsert(sql, ads, new JDBCHelper.InsertCallback() {
                @Override
                public void process(String sql, PreparedStatement preparedStatement, Object[] objects) throws Exception {
                    for (Ad ad : (Ad[]) ads) {
                        preparedStatement.setObject(1, ad.getAdId());
                        preparedStatement.setObject(2, ad.getClickDay());
                        preparedStatement.setObject(3, ad.getClickTime());
                        preparedStatement.setObject(4, ad.getClickNumber());
                        preparedStatement.setObject(5, ad.getClickNumber());
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
