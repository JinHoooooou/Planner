package com.kh.servlet.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate2 {
	//	1. Connection 객체 생성한 후 생성된 Connection 객체를 반환해주는 getConnection 메소드
	  public static Connection getConnection() {
		    Connection connection = null;
		    try {
		      Class.forName("oracle.jdbc.driver.OracleDriver");
		      connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SEMI", "SEMI");
		     
		      connection.setAutoCommit(false);

		    } catch (ClassNotFoundException | SQLException e) {
		      e.printStackTrace();
		    }
		    return connection;
		  }
		  
			
			public static void commit(Connection conn) {
				try {
					if(conn != null && !conn.isClosed()) {
						conn.commit();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			public static void rollback(Connection conn) {
				try {
					
					if(conn != null && !conn.isClosed()) {
						conn.rollback();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
			}
			
			public static void close(Statement stmt) {
				try {
					
					if(stmt != null && !stmt.isClosed()) {
						stmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			public static void close(Connection conn) {
				try {
					
					if(conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			public static void close(ResultSet rset) {
				try {
					
					if(rset != null && !rset.isClosed()) {
						rset.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
}

