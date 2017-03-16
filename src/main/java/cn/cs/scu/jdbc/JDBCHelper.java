package cn.cs.scu.jdbc;

import cn.cs.scu.constants.Constants;
import cn.cs.scu.conf.ConfigurationManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;


/**
 * JDBC辅助组件
 * <p>
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */

public class JDBCHelper {

    static {
        String driver = ConfigurationManager.getString(Constants.JDBC_DRIVER);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // JDBCHelper的单例化
    private static JDBCHelper instanse = null;

    public static JDBCHelper getInstanse() {
        if (instanse == null) {
            synchronized (JDBCHelper.class) {
                if (instanse == null) {
                    instanse = new JDBCHelper();
                }
            }

        }
        return instanse;
    }

    //数据库连接池
    private LinkedList<Connection> datasource = new LinkedList<Connection>();


    /**
     * 实现单例的过程中，创建唯一的数据库连接池
     */
    private JDBCHelper() {
        int datasourceSize = ConfigurationManager.getInteger(Constants.JDBC_DATASOURCE_SIZE);
        //创建指定连接数量的数据库连接池
        for (int i = 0; i < datasourceSize; i++) {
            boolean local = ConfigurationManager.getBoolean(Constants.SPARK_LOCAL);
            String url = null;
            String user = null;
            String password = null;

            if (local) {
                url = ConfigurationManager.getString(Constants.JDBC_URL);
                user = ConfigurationManager.getString(Constants.JDBC_USER);
                password = ConfigurationManager.getString(Constants.JDBC_PASSWORD);
            } else {
                url = ConfigurationManager.getString(Constants.JDBC_URL_PROD);
                user = ConfigurationManager.getString(Constants.JDBC_USER_PROD);
                password = ConfigurationManager.getString(Constants.JDBC_PASSWORD_PROD);
            }

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                datasource.push(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 提供获取数据库连接的方法,有可能，你去获取的时候，这个时候，连接都被用光了，你暂时获取不到数据库连接
     * 所以我们要自己编码实现一个简单的等待机制，去等待获取到数据库连接
     *
     * @return
     */
    private synchronized Connection getConnection() {
        while (datasource.size() == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return datasource.poll();
    }


    /**
     * 执行查询安全SQL语句
     *
     * @param sql
     * @param params
     * @param callback
     */
    public void executeQuery(String sql, Object[] params, QueryCallback callback) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            rs = pstmt.executeQuery();
            callback.process(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                datasource.push(conn);
            }
        }
    }

    /**
     * 执行查询SQL语句
     *
     * @param sql
     * @param callback
     */
    public void executeQuery(String sql, QueryCallback callback) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            callback.process(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                datasource.push(conn);
            }
        }
    }


    /**
     * 插入数据
     *
     * @param sql
     */
    public void excuteInsert(String sql, Object[] objects, InsertCallback insertCallback) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            insertCallback.process(sql, pstmt, objects);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                datasource.push(conn);
            }
        }
    }

    /**
     * 静态内部类：查询回调接口
     *
     * @author Administrator
     */
    public interface QueryCallback {

        /**
         * 处理查询结果
         *
         * @param rs
         * @throws Exception
         */
        void process(ResultSet rs) throws Exception;

    }

    public interface InsertCallback {
        /**
         * 处理插入对象
         *
         * @param sql
         * @param preparedStatement
         * @param objects
         * @throws Exception
         */
        void process(String sql, PreparedStatement preparedStatement, Object[] objects) throws Exception;
    }

}
