package com.jqq.demotest;

import com.jqq.config.XmlConfig;

public class xmlConfigDemoTest {
	public static void main(String[] args) throws Exception{
		XmlConfig config = new XmlConfig();
		String url = config.getValue("mysql", "url");
		System.out.println("url:" + url);
		String path1 = Class.class.getClass().getResource("/").getPath();
		System.out.println("path1:" + path1);
	}
}
