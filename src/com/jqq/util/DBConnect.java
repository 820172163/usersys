package com.jqq.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.jqq.config.XmlConfig;

/**
 * 数据库连接类
 * @author jqq
 *
 */
public class DBConnect {
	private static Logger log = Logger.getLogger(DBConnect.class);
	public static String url = ""; //数据库连接url
	public static String driver = ""; //数据库连接驱动
	public static String username = ""; //数据库连接用户名
	public static String password = ""; //数据库连接密码
	public static Connection conn = null;
	public static PreparedStatement pstmt = null;
	public static ResultSet rs = null;
	
	//读取系统数据库配置
	static{
		XmlConfig conf = new XmlConfig();
		url = conf.getValue("mysql", "url");
		driver= conf.getValue("mysql", "driver");
		username= conf.getValue("mysql", "username");
		password= conf.getValue("mysql", "password");
	}
	
	/**
	 * 初始化数据库连接
	 */
	public static void init(){
		try{
			if(conn == null){
				log.debug("================注册JDBC驱动================");
				Class.forName(driver);
				log.debug("================打开数据库连接================");
				conn = DriverManager.getConnection(url, username, password);
			}
		}catch(Exception e){
			log.debug("================[DBConnect] 数据库驱动init初始化失败================");
			e.printStackTrace();
			System.out.println("================[DBConnect] 数据库驱动init初始化失败================");
		}finally{
			
		}
	}
	/**
	 * 关闭数据库连接
	 */
	public static void close(){
		try{
			if(conn != null){
				log.debug("================关闭数据库连接================");
				conn.close();
				conn = null;
			}
		}catch(Exception e){
			log.debug("================[DBConnect] 数据库连接close关闭失败================");
			e.printStackTrace();
			System.out.println("================[DBConnect] 数据库连接close关闭失败================");
		}finally{
			
		}
	}
	/**
	 * 根据sql语句查询数据
	 * @param sql
	 * @return
	 */
	public static ResultSet selectSql(String sql){
		try{
			pstmt = conn.prepareStatement(sql);
			log.debug("================[selectSql]查询sql:" + sql);
			rs = pstmt.executeQuery();
		}catch(Exception e){
			log.debug("================[DBConnect] 数据库驱动init初始化失败================");
			e.printStackTrace();
			System.out.println("================[DBConnect] 数据库驱动init初始化失败================");
		}finally{
			
		}
		return rs;
	}
	/**
	 * 根据sql执行新增、修改或删除
	 * @param sql
	 * @return
	 */
	public static boolean excuteSql(String sql){
		boolean result = false;
		try{
			pstmt = conn.prepareStatement(sql);
			log.debug("================[excuteSql]执行sql:" + sql);
			int flag = pstmt.executeUpdate();
			log.debug("================[excuteSql]数据更新行数:" + flag);
			result = true;
		}catch(Exception e){
			log.debug("================[DBConnect] excuteSql执行sql异常================");
			e.printStackTrace();
			System.out.println("================[DBConnect] excuteSql执行sql异常================");
		}finally{
			
		}
		return result;
	}
}