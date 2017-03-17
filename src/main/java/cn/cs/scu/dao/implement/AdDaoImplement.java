package cn.cs.scu.dao.implement;

import cn.cs.scu.constants.Constants;
import cn.cs.scu.dao.DaoImplement;
import cn.cs.scu.domain.Ad;
import cn.cs.scu.javautils.SqlUtils;
import cn.cs.scu.jdbc.JDBCHelper;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * 广告表数据访问对象实现类
 * <p>
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class AdDaoImplement extends DaoImplement {

    /**
     * 更新广告表
     *
     * @param ads
     */
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
            jdbcHelper.excuteInsert(sql, ads, (sql1, preparedStatement, objects) -> {
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
            });
        }
    }

    /**
     * 获得广告表数据数据
     *
     * @param param
     * @return
     */

    @Override
    public Object[] getTable(JSONObject param) {
        String sql = "SELECT * FROM " + Constants.TABLE_AD;
        Long ad_id = null;
        String ad_type = null;
        String click_day = null;
        String click_time = null;
        Long click_number = null;

        // 查询语句参数
        ArrayList<Object> paramLists = new ArrayList<>();

        try {
            ad_id = param.getLong(Constants.FIELD_AD_ID);
            String currentSql = Constants.FIELD_AD_ID + " =?";
            paramLists.add(ad_id);
            sql = SqlUtils.concatSQL(sql, currentSql);
        } catch (JSONException e) {
            System.out.println("key: ad_id doesn't exist");
        }

        try {
            ad_type = param.getString(Constants.FIELD_AD_TYPE);
            String currentSql = Constants.FIELD_AD_TYPE + " =?";
            paramLists.add(ad_type);
            sql = SqlUtils.concatSQL(sql, currentSql);
        } catch (JSONException e) {
            System.out.println("key: ad_type doesn't exist");
        }

        try {
            click_day = param.getString(Constants.FIELD_CLICK_DAY);
            String currentSql = Constants.FIELD_CLICK_DAY + " =?";
            paramLists.add(click_day);
            sql = SqlUtils.concatSQL(sql, currentSql);
        } catch (JSONException e) {
            System.out.println("key: click_day doesn't exist");
        }

        try {
            click_time = param.getString(Constants.FIELD_CLICK_TIME);
            String currentSql = Constants.FIELD_CLICK_TIME + " =?";
            paramLists.add(click_time);
            sql = SqlUtils.concatSQL(sql, currentSql);
        } catch (JSONException e) {
            System.out.println("key: click_time doesn't exist");
        }

        try {
            click_number = param.getLong(Constants.FIELD_CLICK_NUMBER);
            String currentSql = Constants.FIELD_CLICK_NUMBER + " =?";
            paramLists.add(click_number);
            sql = SqlUtils.concatSQL(sql, currentSql);
        } catch (JSONException e) {
            System.out.println("key: click_number doesn't exist");
        }

        // jdbc单例
        JDBCHelper jdbcHelper = JDBCHelper.getInstanse();
        ArrayList<Ad> ads = new ArrayList<>();
        jdbcHelper.executeQuery(sql, paramLists.toArray(), rs -> {
            while (rs.next()) {
                Ad ad = new Ad();
                ad.setAdId(rs.getLong(1));
                ad.setAdType(rs.getString(2));
                ad.setClickDay(rs.getString(3));
                ad.setClickTime(rs.getString(4));
                ad.setClickNumber(rs.getLong(5));
                ads.add(ad);
            }
        });
        return ads.toArray(new Ad[0]).clone();
    }
}
