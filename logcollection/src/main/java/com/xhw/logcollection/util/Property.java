package com.xhw.logcollection.util;

import java.io.IOException;
import java.util.Properties;

@Deprecated
public class Property {
	public String driver;
	public String url;
	public String user;
	public String pwd;
	/*
	 * 实现单例模式
	 * 1.私有化构造函数
	 * 2.提供一个静态的，本类的对象
	 * 3.对外提供一个方法，用于获取本类的对象
	 * */
	private Property() {
		super();
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getResourceAsStream("/application.properties"));
			driver = prop.getProperty("admin.datasource.driverClassName");
			url = prop.getProperty("admin.datasource.url");
			user = prop.getProperty("admin.datasource.username");
			pwd = prop.getProperty("admin.datasource.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static final Property property = new Property();
	
	public static Property getInstance(){
		return property;
	}
	
	
}
