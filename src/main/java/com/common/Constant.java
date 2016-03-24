package com.common;

import com.cache.ConfigUtil;

public class Constant {
	
	static ConfigUtil config = new ConfigUtil("application.properties");
	public static final String MAX_LOGIN_TIMES = config.get("max.login.times");
	
	
//	public static Properties props;
//	
//	static {
//		Resource resource = new ClassPathResource("application.properties");
//		try {
//			props = PropertiesLoaderUtils.loadProperties(resource);
//		}
//		catch(IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static final String IP_SHANDONG = props.getProperty("ip.shandong");
//	
//	public static final String MAX_LOGIN_TIMES = props.getProperty("max.login.times");
	
}
