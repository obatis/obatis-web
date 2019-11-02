package com.obatis.startup;

import com.obatis.config.SystemConstant;
import com.obatis.config.response.ResponseResultHandleFactory;
import com.obatis.validate.ValidateTool;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.Resource;

@Configuration
@Order(1)
public class StartupApplicationRunner extends SpringApplication implements ApplicationRunner  {

	@Resource
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	@Resource
	private Environment env;

	/**
	 * 该方法在项目启动时运行
	 */
	@Override
	public void run(ApplicationArguments args) {
		SystemConstant.ENV = env;
		this.loadConfig();
		// 加载返回封装
		ResponseResultHandleFactory.handleResponseResultInfo(requestMappingHandlerAdapter);
	}

	private void loadConfig() {
		SystemConstant.SERVICE_NAME = env.getProperty("spring.application.name");
		String runEvn =  env.getProperty("spring.profiles.active");
		if(ValidateTool.isEmpty(runEvn) || runEvn.toLowerCase().equals("dev")) {
			SystemConstant.RUN_DEV_ENV = true;
		}
	}
}
