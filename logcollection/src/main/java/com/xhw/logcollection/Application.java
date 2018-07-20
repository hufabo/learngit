package com.xhw.logcollection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ImportResource({"classpath:applicationContext.xml"})
@EnableSwagger2
@EnableCaching  //开启缓存功能
@EnableScheduling //开启计划任务支持
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	//日志
	private static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws Exception {
		logger.info("服务器,开始启动");
		SpringApplication.run(Application.class, args);
		logger.info("服务器,启动完毕");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
}
