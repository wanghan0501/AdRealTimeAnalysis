package cn.cs.scu.dao.implement;

import cn.cs.scu.constants.Constants;
import cn.cs.scu.dao.DaoImplement;
import cn.cs.scu.domain.Ad;
import cn.cs.scu.domain.UserClick;
import cn.cs.scu.jdbc.JDBCHelper;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * 用户点击表数据访问对象实现类
 * <p>
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class UserClickDaoImplement extends DaoImplement {
    @Override
    public void updateTable(Object[] userClicks) {
        if (userClicks instanceof UserClick[]) {
            // jdbc单例
            JDBCHelper jdbcHelper = JDBCHelper.getInstanse();
            // 如果key不存在则插入，如果存在则更新
            String sql = "INSERT INTO " + Constants.TABLE_USER_CLICK + "(" + Constants.FIELD_USER_ID + "," +
                    Constants.FIELD_AD_ID + "," + Constants.FIELD_CLICK_DAY + "," +
                    Constants.FIELD_CLICK_NUMBER + ") VALUE(?,?,?,?) ON DUPLICATE KEY UPDATE " +
                    Constants.FIELD_CLICK_NUMBER + "=?";

            // mysql批量插入数据
            jdbcHelper.excuteInsert(sql, userClicks, (sql1, preparedStatement, objects) -> {
                for (UserClick userClick : (UserClick[]) userClicks) {
                    preparedStatement.setObject(1, userClick.getUserId());
                    preparedStatement.setObject(2, userClick.getAdId());
                    preparedStatement.setObject(3, userClick.getClickDay());
                    preparedStatement.setObject(4, userClick.getClickNum());
                    preparedStatement.setObject(5, userClick.getClickNum());
                    preparedStatement.addBatch();
                }
                // 执行批量插入
                preparedStatement.executeBatch();
            });
        }
    }

    @Override
    public Object[] getTable(JSONObject param) {
        return new Object[0];
    }
}
