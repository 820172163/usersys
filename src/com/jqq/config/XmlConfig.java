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
	//xml�ļ�·��
	private static String xmlPath = System.getProperty("user.dir") + "/classes/com/jqq/config/sysconfig.xml";
	//��ͬ���ͷ����������ļ�����·������ͬ
	static{
		if(DeployServer.isWeblogic() || DeployServer.isWebSphere()){
			xmlPath = System.getProperty("user.dir") + "/classes/com/jqq/config/sysconfig.xml";
		}else{
			xmlPath = Class.class.getClass().getResource("/").getPath() + "com/jqq/config/sysconfig.xml";
		}
	}
	//xml�ļ�����
	private Document document;
	private static final Object lock = new Object(); //�̼߳���
	/**
	 * ������
	 */
	public XmlConfig(){
		synchronized(lock){
			if(document == null){
				//����״ζ�ȡû�г�ʼ������ȡxml�����ļ���ʼ��
				SAXBuilder builder = new SAXBuilder();
				try {
					document = builder.build(xmlPath);
				} catch (JDOMException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				//�Ѿ���ʼ���������ٴγ�ʼ��
			}
		}
	}
	/**
	 * ��ȡָ���ڵ���cansuzhi
	 * @param rootName
	 * @param nodeName
	 * @return
	 */
	public String getValue(String rootName, String nodeName){
		String result = "";
		try{
			//�ڵ����Ʋ�����Ϊ��
			if(rootName!=null && !"".equals(rootName.trim()) && nodeName!=null && !"".equals(nodeName.trim())){
				Element rootElement = document.getRootElement();
				String[] rootArr = rootName.split(",");
				Element targetElement = null;
				//��ָ���ڵ㿪ʼ��������ȡ����Ԫ��
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
							//ƥ�䵽��Ӧparam����
							if(nodeName.equals(tempElement.getAttributeValue("key"))){
								//��ȡ��Ӧparam���õ�valueֵ
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
