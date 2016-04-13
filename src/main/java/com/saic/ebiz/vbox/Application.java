package com.saic.ebiz.vbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 程序启动类
 *
 */
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static ApplicationContext context;
	
    public static void main(String[] args) {
		logger.info("开始加载系统xml配置...");
		context = new ClassPathXmlApplicationContext(new String[] { "conf/spring-context.xml" });
		GprsServerBootstrap bootstrap = (GprsServerBootstrap)context.getBean("gprsServerBootstrap");
		logger.error("加载系统xml配置结束,这里永远都不会执行到...{}",bootstrap.toString());
	}
	
}
