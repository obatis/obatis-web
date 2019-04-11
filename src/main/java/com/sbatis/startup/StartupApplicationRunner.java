package com.sbatis.startup;

import com.sbatis.config.response.HandleResponseResultInfoFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.Resource;

@Configuration
@Order(1)
public class StartupApplicationRunner extends SpringApplication implements ApplicationRunner  {

	@Resource
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

	/**
	 * 该方法在项目启动时运行
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {

		System.out.println(" ######### StartupApplicationRunner ###############");

		// 加载返回封装
		HandleResponseResultInfoFactory.handleResponseResultInfo(requestMappingHandlerAdapter);
	}

}
