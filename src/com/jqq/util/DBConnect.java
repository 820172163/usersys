package com.jqq.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.jqq.config.XmlConfig;

/**
 * ���ݿ�������
 * @author jqq
 *
 */
public class DBConnect {
	private static Logger log = Logger.getLogger(DBConnect.class);
	public static String url = ""; //���ݿ�����url
	public static String driver = ""; //���ݿ���������
	public static String username = ""; //���ݿ������û���
	public static String password = ""; //���ݿ���������
	public static Connection conn = null;
	public static PreparedStatement pstmt = null;
	public static ResultSet rs = null;
	
	//��ȡϵͳ���ݿ�����
	static{
		XmlConfig conf = new XmlConfig();
		url = conf.getValue("mysql", "url");
		driver= conf.getValue("mysql", "driver");
		username= conf.getValue("mysql", "username");
		password= conf.getValue("mysql", "password");
	}
	
	/**
	 * ��ʼ�����ݿ�����
	 */
	public static void init(){
		try{
			if(conn == null){
				log.debug("================ע��JDBC����================");
				Class.forName(driver);
				log.debug("================�����ݿ�����================");
				conn = DriverManager.getConnection(url, username, password);
			}
		}catch(Exception e){
			log.debug("================[DBConnect] ���ݿ�����init��ʼ��ʧ��================");
			e.printStackTrace();
			System.out.println("================[DBConnect] ���ݿ�����init��ʼ��ʧ��================");
		}finally{
			
		}
	}
	/**
	 * �ر����ݿ�����
	 */
	public static void close(){
		try{
			if(conn != null){
				log.debug("================�ر����ݿ�����================");
				conn.close();
				conn = null;
			}
		}catch(Exception e){
			log.debug("================[DBConnect] ���ݿ�����close�ر�ʧ��================");
			e.printStackTrace();
			System.out.println("================[DBConnect] ���ݿ�����close�ر�ʧ��================");
		}finally{
			
		}
	}
	/**
	 * ����sql����ѯ����
	 * @param sql
	 * @return
	 */
	public static ResultSet selectSql(String sql){
		try{
			pstmt = conn.prepareStatement(sql);
			log.debug("================[selectSql]��ѯsql:" + sql);
			rs = pstmt.executeQuery();
		}catch(Exception e){
			log.debug("================[DBConnect] ���ݿ�����init��ʼ��ʧ��================");
			e.printStackTrace();
			System.out.println("================[DBConnect] ���ݿ�����init��ʼ��ʧ��================");
		}finally{
			
		}
		return rs;
	}
	/**
	 * ����sqlִ���������޸Ļ�ɾ��
	 * @param sql
	 * @return
	 */
	public static boolean excuteSql(String sql){
		boolean result = false;
		try{
			pstmt = conn.prepareStatement(sql);
			log.debug("================[excuteSql]ִ��sql:" + sql);
			int flag = pstmt.executeUpdate();
			log.debug("================[excuteSql]���ݸ�������:" + flag);
			result = true;
		}catch(Exception e){
			log.debug("================[DBConnect] excuteSqlִ��sql�쳣================");
			e.printStackTrace();
			System.out.println("================[DBConnect] excuteSqlִ��sql�쳣================");
		}finally{
			
		}
		return result;
	}
}
