package com.obatis.startup;

import com.obatis.config.SystemConstant;
import com.obatis.config.response.ResponseResultHandleFactory;
import com.obatis.config.response.result.callback.ExceptionRestHandle;
import com.obatis.config.response.result.callback.ExceptionRestHandleCallback;
import com.obatis.config.response.result.callback.HandleExceptionCallbackContext;
import com.obatis.tools.ValidateTool;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.Resource;
import java.util.Map;

@Configuration
@Order(1)
public class StartupApplicationRunner extends SpringApplication implements ApplicationRunner  {

	@Resource
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	@Resource
	private Environment env;
	@Resource
	private ApplicationContext applicationContext;

	/**
	 * 该方法在项目启动时运行
	 */
	@Override
	public void run(ApplicationArguments args) {

		SystemConstant.ENV = env;
		this.loadConfig();
		// 加载返回封装
		ResponseResultHandleFactory.handleResponseResultInfo(requestMappingHandlerAdapter);
		/**
		 * 处理异常回调
		 */
		this.handleExceptionCall();
	}

	private void loadConfig() {
		SystemConstant.SERVICE_NAME = env.getProperty("spring.application.name");
		String runEvn =  env.getProperty("spring.profiles.active");
		if(ValidateTool.isEmpty(runEvn) || runEvn.toLowerCase().equals("dev")) {
			SystemConstant.RUN_DEV_ENV = true;
		}
	}

	/**
	 * 异常回调处理
	 */
	private void handleExceptionCall() {
		Map<String, ExceptionRestHandleCallback> beanMap = applicationContext.getBeansOfType(ExceptionRestHandleCallback.class);
		if(beanMap != null && !beanMap.isEmpty()) {
			ExceptionRestHandle.ADD_EXCEPTION_FLAG = true;
			SystemConstant.HANDLE_POOL.execute(new HandleExceptionCallbackContext(beanMap));
		}

	}

}
