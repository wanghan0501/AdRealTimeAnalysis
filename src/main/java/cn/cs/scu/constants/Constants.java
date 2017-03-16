package cn.cs.scu.constants;

/**
 * 配置常量接口类
 * <p>
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */

public interface Constants {
    String SPARK_MASTER = "local";
    String SPARK_LOCAL = "spark.local";
    String SPARK_APP_NAME_SESSION = "AdRealTimeAnalysis";
    String SPARK_STREAMING_COLLECT_TIME = "spark.streaming.collect.time";

    String JDBC_DRIVER = "jdbc.driver";
    String JDBC_URL = "jdbc.url";
    String JDBC_USER = "jdbc.user";
    String JDBC_PASSWORD = "jdbc.password";
    String JDBC_USER_PROD = "";
    String JDBC_URL_PROD = "";
    String JDBC_PASSWORD_PROD = "";
    String JDBC_DATASOURCE_SIZE = "jdbc.datasource.size";

    String TABLE_AD = "ad";
    String TABLE_BLACKLIST = "blacklist";
    String TABLE_PROVINCE_CLICK = "province_click";
    String TABLE_USER = "user";
    String TABLE_USER_CLICK = "user_click";

    String FIELD_USER_ID = "user_id";
    String FIELD_USER_NAME = "user_name";

    String FIELD_AD_ID = "ad_id";
    String FIELD_AD_TYPE = "ad_type";
    String FIELD_CLICK_DAY = "click_day";
    String FIELD_CLICK_TIME = "click_time";
    String FIELD_CLICK_NUMBER = "click_number";
    String FIELD_PROVINCE = "province";
    String FIELD_CITY = "city";

}
