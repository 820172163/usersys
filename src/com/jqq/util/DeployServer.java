package com.jqq.util;

import org.apache.log4j.Logger;

public class DeployServer {
	private static Logger log = Logger.getLogger(DeployServer.class);
	private String serverType; //部署服务类型
	public static final String TOMCAT = "tomcat";
	public static final String JBOSS = "jboss";
	public static final String WEBLOGIC = "weblogic";
	public static final String WEBSPHERE = "websphere";
	private boolean tomcatFlag;
	private boolean jbossFlag;
	private boolean weblogicFlag;
	private boolean websphereFlag;
	private static DeployServer deployServer = new DeployServer();
	private DeployServer(){
		if(_isTomcat()){
			this.tomcatFlag = true;
			this.serverType = this.TOMCAT;
		}else if(_isJboss()){
			this.jbossFlag = true;
			this.serverType = this.JBOSS;
		}else if(_isWeblogic()){
			this.weblogicFlag = true;
			this.serverType = this.WEBLOGIC;
		}else if(_isWebSphere()){
			this.websphereFlag = true;
			this.serverType = this.WEBSPHERE;
		}
	}
	/**
	 * 获取发布容器类型名称
	 * @return
	 */
	public static String getDeployServerType(){
		return deployServer.serverType;
	}
	public static boolean isTomcat(){
		return deployServer.tomcatFlag;
	}
	public static boolean isJboss(){
		return deployServer.jbossFlag;
	}
	public static boolean isWeblogic(){
		return deployServer.weblogicFlag;
	}
	public static boolean isWebSphere(){
		return deployServer.websphereFlag;
	}
	/**
	 * 判断是否是指定发布容器类型
	 * @param className
	 * @return
	 */
	private boolean isSpecifyServer(String className){
		boolean result = false;
		try{
			ClassLoader loader = ClassLoader.getSystemClassLoader();
			loader.loadClass(className);
			result = true;
		}catch(ClassNotFoundException e){
			log.debug("================[DeployServer] isSpecifyServer class not Found================");
			Class c = getClass();
			if(c.getResource(className) != null){
				log.debug("================[DeployServer] isSpecifyServer getResource class================");
				result = true;
			}
		}finally{
			
		}
		return result;
	}
	/**
	 * 是否tomcat发布容器
	 * @return
	 */
	private boolean _isTomcat(){
		boolean flag = isSpecifyServer("/org/apache/catalina/startup/Bootstrap.class");
		if(!flag){
			flag = isSpecifyServer("/org/apache/catalina/startup/Embedded.class");
		}
		return flag;
	}
	/**
	 * 是否jboss发布容器
	 * @return
	 */
	private boolean _isJboss(){
		boolean flag = isSpecifyServer("/org/jboss/Main.class");
		return flag;
	}
	/**
	 * 是否weblogic发布容器
	 * @return
	 */
	private boolean _isWeblogic(){
		boolean flag = isSpecifyServer("/weblogic/Server.class");
		return flag;
	}
	/**
	 * 是否websphere发布容器
	 * @return
	 */
	private boolean _isWebSphere(){
		boolean flag = isSpecifyServer("/com/ibm/websphere/product/VersionInfo.class");
		return flag;
	}
	
}