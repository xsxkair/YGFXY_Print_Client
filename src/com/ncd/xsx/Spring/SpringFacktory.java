package com.ncd.xsx.Spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringFacktory {
	
	private static ApplicationContext ctx = null;
	
	public static void SpringFacktoryInit(){
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	public static ApplicationContext getCtx() {
		return ctx;
	}

	public static <T> T GetBean(Class<T> arg0){
		return ctx.getBean(arg0);
	}
}
