package cn.cs.scu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Wanghan on 2017/3/15.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class testMysql {
    public static void main(String[] args) {
        String sql = "SELECT * FROM blacklist";

        String driver = "com.mysql.jdbc.Driver";

        String host = "localhost";

        String user = "root";

        String pwd = "scubigdata";

        Connection conn = null;

        Statement stmt = null;
        try {
            Class.forName(driver).newInstance();
            conn = (Connection) DriverManager.getConnection("jdbc:MySQL://localhost:3306/ad?characterEncoding=utf8&useSSL=true", user, pwd);
            stmt = (Statement) conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            //System.out.println(rs.getFetchSize());
            while (rs.next()){
                System.out.println(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
