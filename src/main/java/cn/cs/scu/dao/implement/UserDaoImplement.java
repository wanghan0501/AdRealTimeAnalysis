package cn.cs.scu.dao.implement;

import cn.cs.scu.constants.Constants;
import cn.cs.scu.dao.DaoImplement;
import cn.cs.scu.domain.User;
import cn.cs.scu.jdbc.JDBCHelper;
import org.json.JSONObject;


/**
 * 用户表数据访问对象实现类
 * <p>
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class UserDaoImplement extends DaoImplement {

    /**
     * 更新用户表
     *
     * @param users
     */
    @Override
    public void updateTable(Object[] users) {
        // user类型检查
        if (users instanceof User[]) {
            // jdbc单例
            JDBCHelper jdbcHelper = JDBCHelper.getInstanse();
            // 如果key不存在则插入，如果存在则更新
            String sql = "INSERT INTO " + Constants.TABLE_USER + "(" + Constants.FIELD_USER_ID + "," +
                    Constants.FIELD_USER_NAME + ") VALUE(?,?)";

            jdbcHelper.excuteInsert(sql, users, (sql1, preparedStatement, objects) -> {
                for (User user : (User[]) users) {
                    preparedStatement.setObject(1, user.getUser_id());
                    preparedStatement.setObject(2, user.getUser_name());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            });
        }
    }

    @Override
    public Object[] getTable(JSONObject param) {
        return new Object[0];
    }
}
