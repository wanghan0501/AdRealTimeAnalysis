package cn.cs.scu.dao.implement;

import cn.cs.scu.constants.Constants;
import cn.cs.scu.dao.DaoImplement;
import cn.cs.scu.domain.Blacklist;
import cn.cs.scu.javautils.SqlUtils;
import cn.cs.scu.jdbc.JDBCHelper;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * 黑名单类数据访问对象实现类
 * <p>
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class BlacklistDaoImplement extends DaoImplement {

    /**
     * 更新黑名单表
     *
     * @param blacklists
     */
    @Override
    public void updateTable(Object[] blacklists) {
        if (blacklists instanceof Blacklist[]) {
            // jdbc单例
            JDBCHelper jdbcHelper = JDBCHelper.getInstanse();
            String sql = "INSERT INTO " + Constants.TABLE_BLACKLIST + "(" + Constants.FIELD_USER_ID + ","
                    + Constants.FIELD_USER_NAME + ") VALUE(?,?)";
            jdbcHelper.excuteInsert(sql, blacklists, new JDBCHelper.InsertCallback() {
                @Override
                public void process(String sql, PreparedStatement preparedStatement, Object[] objects) throws Exception {
                    for (Blacklist blacklist : (Blacklist[]) blacklists) {
                        preparedStatement.setObject(1, blacklist.getUser_id());
                        preparedStatement.setObject(2, blacklist.getUser_name());

                        preparedStatement.addBatch();
                    }
                    // 批量插入
                    preparedStatement.executeBatch();
                }
            });
        }
    }

    /**
     * 获取黑名单表
     *
     * @param param
     * @return
     */
    @Override
    public Object[] getTable(JSONObject param) {
        String sql = "SELECT * FROM " + Constants.TABLE_BLACKLIST;
        Long user_id = null;
        String user_name = null;
        try {
            user_id = param.getLong(Constants.FIELD_USER_ID);
            user_name = param.getString(Constants.FIELD_USER_NAME);
        } catch (JSONException e) {

        }

        if (user_id != null) {
            String currentSql = Constants.FIELD_USER_ID + " = " + user_id;
            sql = SqlUtils.concatSQL(sql, currentSql);
        }
        if (user_name != null) {
            String currentSql = Constants.FIELD_USER_NAME + " = \"" + user_name + "\"";
            sql = SqlUtils.concatSQL(sql, currentSql);
        }

        // jdbc单例
        JDBCHelper jdbcHelper = JDBCHelper.getInstanse();
        ArrayList<Blacklist> arrayList = new ArrayList<>();

        jdbcHelper.executeQuery(sql, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()) {
                    Blacklist blacklist = new Blacklist();
                    blacklist.setUser_id(rs.getString(1));
                    blacklist.setUser_name(rs.getString(2));
                    arrayList.add(blacklist);
                }

            }
        });

        // 返回黑名单数组对象的克隆，防止连接关闭后，数据被清空
        return arrayList.toArray(new Blacklist[0]).clone();
    }

}
