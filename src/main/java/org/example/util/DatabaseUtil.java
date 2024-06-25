package org.example.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {

    private static final Properties props = new Properties();
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD ;

    static {
        try {
            FileInputStream fis = new FileInputStream("src\\main\\resources\\config.properties");
            props.load(fis);
            URL = props.getProperty("db.url");
            USERNAME = props.getProperty("db.username");
            PASSWORD = props.getProperty("db.password");
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("無法連接文件", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
