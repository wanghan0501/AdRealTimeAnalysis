package cn.cs.scu.dao.implement;

import cn.cs.scu.constants.Constants;
import cn.cs.scu.dao.DaoImplement;
import cn.cs.scu.domain.Ad;
import cn.cs.scu.domain.ProvinceClick;
import cn.cs.scu.domain.ProvinceTop3Ad;
import cn.cs.scu.jdbc.JDBCHelper;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * 省份广告点击数据访问对象实现类
 * <p>
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class ProvinceClickDaoImplement extends DaoImplement {

    /**
     * 更新省份点击表
     *
     * @param provinceClicks
     */
    @Override
    public void updateTable(Object[] provinceClicks) {
        if (provinceClicks instanceof ProvinceClick[]) {
            // jdbc单例
            JDBCHelper jdbcHelper = JDBCHelper.getInstanse();
            // 如果key不存在则插入，如果存在则更新
            String sql = "INSERT INTO " + Constants.TABLE_PROVINCE_CLICK + "(" + Constants.FIELD_PROVINCE + "," +
                    Constants.FIELD_CITY + "," + Constants.FIELD_AD_ID + "," + Constants.FIELD_CLICK_DAY + "," +
                    Constants.FIELD_CLICK_NUMBER + ") VALUE(?,?,?,?,?) ON DUPLICATE KEY UPDATE " +
                    Constants.FIELD_CLICK_NUMBER + "=?";

            // mysql批量插入数据
            jdbcHelper.excuteInsert(sql, provinceClicks, (sql1, preparedStatement, objects) -> {
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
            });
        }
    }

    /**
     * 根据查询参数得到省份点击表
     *
     * @param param
     * @return
     */
    @Override
    public Object[] getTable(JSONObject param) {
        return new Object[0];
    }


    /**
     * 返回各省top3热门广告
     *
     * @return
     */
    public ProvinceTop3Ad[] getTop3Ad(JSONObject param) {
        String sql = "SELECT province,ad_id,SUM(click_number) AS click_number FROM " + Constants.TABLE_PROVINCE_CLICK + " WHERE click_day = ?" +
                " GROUP BY province,ad_id ORDER BY province,click_number DESC";
        String click_day = null;
        // 查询语句参数
        ArrayList<Object> paramLists = new ArrayList<>();

        try {
            click_day = param.getString(Constants.FIELD_CLICK_DAY);
            paramLists.add(click_day);
        } catch (JSONException e) {
            System.out.println("key: click_day doesn't exist");
        }

        // jdbc单例
        JDBCHelper jdbcHelper = JDBCHelper.getInstanse();
        ArrayList<ProvinceTop3Ad> provinceTop3Ads = new ArrayList<>();
        jdbcHelper.executeQuery(sql, paramLists.toArray(), rs -> {
            String lastProvince = null;
            ProvinceTop3Ad provinceTop3Ad = null;
            int topNum = 0;

            while (rs.next()) {
                String currentProvince = rs.getString(1);
                if (!currentProvince.equals(lastProvince)) {
                    if (provinceTop3Ad != null) {
                        provinceTop3Ad.setProvince(lastProvince);
                        provinceTop3Ads.add((ProvinceTop3Ad) provinceTop3Ad.clone());
                    }
                    // 如果有一个新省份出现，则新建一个省份热门广告对象
                    provinceTop3Ad = new ProvinceTop3Ad();
                    topNum = 0;
                }
                // top1
                if (topNum == 0) {
                    Long ad_id = rs.getLong(2);
                    Long click_number = rs.getLong(3);
                    Ad top1 = new Ad();
                    top1.setAdId(ad_id);
                    top1.setClickNumber(click_number);
                    provinceTop3Ad.setTop1(top1);
                }
                // top2
                if (topNum == 1) {
                    Long ad_id = rs.getLong(2);
                    Long click_number = rs.getLong(3);
                    Ad top2 = new Ad();
                    top2.setAdId(ad_id);
                    top2.setClickNumber(click_number);
                    provinceTop3Ad.setTop2(top2);
                }
                // top3
                if (topNum == 2) {
                    Long ad_id = rs.getLong(2);
                    Long click_number = rs.getLong(3);
                    Ad top3 = new Ad();
                    top3.setAdId(ad_id);
                    top3.setClickNumber(click_number);
                    provinceTop3Ad.setTop3(top3);
                }
                topNum++;
                lastProvince = currentProvince;

                // 如果是查询结果遍历结束，将热门广告加入列表
                if (rs.isLast()) {
                    provinceTop3Ad.setProvince(lastProvince);
                    provinceTop3Ads.add((ProvinceTop3Ad) provinceTop3Ad.clone());
                }
            }
        });
        return provinceTop3Ads.toArray(new ProvinceTop3Ad[0]).clone();
    }
}
