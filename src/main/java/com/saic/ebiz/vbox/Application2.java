package com.saic.ebiz.vbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import de.codecentric.boot.admin.config.EnableAdminServer;

/**
 * 程序启动类
 *
 */

@SpringBootApplication
@EnableAdminServer

//@ComponentScan("com.saic.ebiz.*")
//@PropertySource("classpath:/config.properties")
@ImportResource("classpath*:conf/spring-context.xml")
public class Application2 {

private static final Logger logger = LoggerFactory.getLogger(Application2.class);
    
    public static void main(String[] args) {
        logger.info("启动项目配置开始...");
        SpringApplication.run(Application2.class, args);
        logger.info("启动项目配置结束...");
    }
	
}
