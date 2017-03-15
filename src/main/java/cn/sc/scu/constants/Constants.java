package cn.sc.scu.constants;

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

    String JDBC_URL = "jdbc.url";
    String JDBC_USER = "jdbc.user";
    String JDBC_DRIVER = "jdbc.driver";
    String JDBC_PASSWORD = "jdbc.password";
    String JDBC_USER_PROD = "";
    String JDBC_URL_PROD = "";
    String JDBC_PASSWORD_PROD = "";
    String JDBC_DATASOURCE_SIZE = "jdbc.datasource.size";
}
