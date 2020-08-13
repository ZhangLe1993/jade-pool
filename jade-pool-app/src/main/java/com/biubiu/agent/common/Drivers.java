package com.biubiu.agent.common;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Drivers {
    static {
        try {
            // 加载数据源 连接驱动
            // DriverManager.registerDriver((java.sql.Driver) Class.forName("com.facebook.presto.jdbc.PrestoDriver").newInstance());
            // DriverManager.registerDriver((java.sql.Driver) Class.forName("org.postgresql.Driver").newInstance());
            // 加载目标库 连接驱动
            DriverManager.registerDriver((java.sql.Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
