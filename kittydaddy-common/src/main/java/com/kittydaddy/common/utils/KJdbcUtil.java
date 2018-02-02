package com.kittydaddy.common.utils;

import java.sql.Connection;     
import java.sql.DriverManager;       
import java.sql.SQLException;
/**
 * @author kitty daddy
 * 连接jdbc工具类
 */
public class KJdbcUtil {
	    private static Connection conn = null;     
	    private static final String URL = "jdbc:mysql://111.231.75.82:3306/kittydaddy?useUnicode=true&characterEncoding=utf8";     
	    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";     
	    private static final String USER_NAME = "root";     
	    private static final String PASSWORD = "183244LUjianhao!";  
	    public static Connection getConnection() {     
	        try {     
	            Class.forName(JDBC_DRIVER);     
	            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);     
	        } catch (ClassNotFoundException e) {     
	            e.printStackTrace();     
	        } catch (SQLException e) {     
	            e.printStackTrace();     
	        }     
	        return conn;     
	    }  
}
