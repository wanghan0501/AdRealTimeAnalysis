package cn.cs.scu.javautils;

/**
 * sql语句工具类
 * <p>
 * Created by Wanghan on 2017/3/16.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class SqlUtils {

    /**
     * 拼接sql查询语句
     *
     * @param totalSql
     * @param currentSql
     * @return
     */
    public static String concatSQL(String totalSql, String currentSql) {
        StringBuilder sqlBuilder = new StringBuilder(currentSql);

        // 加入where
        totalSql = trimSpace(totalSql);
        if (!totalSql.contains("WHERE")) {
            totalSql += " WHERE ";
            sqlBuilder.insert(0, totalSql);
        } else {
            sqlBuilder.insert(0, totalSql + " AND ");
        }

        return sqlBuilder.toString();
    }

    /**
     * 去除sql查询语句行首行尾空格
     *
     * @param sql
     * @return
     */
    public static String trimSpace(String sql) {
        //  去除行尾空格
        while (sql.endsWith(" ")) {
            sql = sql.substring(0, sql.length() - 1);
        }
        while (sql.startsWith(" ")) {
            sql = sql.substring(1);
        }

        return sql;
    }
}
