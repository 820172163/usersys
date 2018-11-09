package com.jqq.config;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import com.jqq.util.DeployServer;

public class XmlConfig {
	private static Logger log = Logger.getLogger(XmlConfig.class);
	//xml文件路径
	private static String xmlPath = System.getProperty("user.dir") + "/classes/com/jqq/config/sysconfig.xml";
	//不同类型发布容器，文件绝对路径处理不同
	static{
		if(DeployServer.isWeblogic() || DeployServer.isWebSphere()){
			xmlPath = System.getProperty("user.dir") + "/classes/com/jqq/config/sysconfig.xml";
		}else{
			xmlPath = Class.class.getClass().getResource("/").getPath() + "com/jqq/config/sysconfig.xml";
		}
	}
	//xml文件对象
	private Document document;
	private static final Object lock = new Object(); //线程加锁
	/**
	 * 构造器
	 */
	public XmlConfig(){
		synchronized(lock){
			if(document == null){
				//如果首次读取没有初始化，读取xml配置文件初始化
				SAXBuilder builder = new SAXBuilder();
				try {
					document = builder.build(xmlPath);
				} catch (JDOMException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				//已经初始化后无需再次初始化
			}
		}
	}
	/**
	 * 获取指定节点下cansuzhi
	 * @param rootName
	 * @param nodeName
	 * @return
	 */
	public String getValue(String rootName, String nodeName){
		String result = "";
		try{
			//节点名称不可以为空
			if(rootName!=null && !"".equals(rootName.trim()) && nodeName!=null && !"".equals(nodeName.trim())){
				Element rootElement = document.getRootElement();
				String[] rootArr = rootName.split(",");
				Element targetElement = null;
				//从指定节点开始遍历，获取到子元素
				for(int i=0; i<rootArr.length; i++){
					if(targetElement != null){
						targetElement = targetElement.getChild(rootArr[i]);
					}else{
						targetElement = rootElement.getChild(rootArr[i]);
					}
				}
				if(targetElement!=null){
					List<Element> targetList = targetElement.getChildren("param");
					if(targetList!=null && targetList.size() > 0){
						Element tempElement = null;
						for(int j=0; j<targetList.size(); j++){
							tempElement = targetList.get(j);
							//匹配到对应param名称
							if(nodeName.equals(tempElement.getAttributeValue("key"))){
								//获取对应param配置的value值
								result = tempElement.getAttributeValue("value");
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return result;
	}
}