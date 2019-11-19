package com.obatis.startup;

import com.obatis.config.SystemConstant;
import com.obatis.config.response.ResponseResultHandleFactory;
import com.obatis.config.response.result.callback.ExceptionRestHandle;
import com.obatis.config.response.result.callback.ExceptionRestHandleCallback;
import com.obatis.config.response.result.callback.ExceptionRestParam;
import com.obatis.validate.ValidateTool;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Order(1)
public class StartupApplicationRunner extends SpringApplication implements ApplicationRunner  {

	private ExecutorService exceptionHandlePool = Executors.newFixedThreadPool(2);

	@Resource
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	@Resource
	private Environment env;
//	@Resource
//	private ExceptionRestHandleCallback exceptionRestHandleCallback;

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

	private void handleExceptionCall() {
//		System.out.println("exceptionRestHandleCallback >>> " + exceptionRestHandleCallback);
	}

	class HandleExceptionCallContext implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					exceptionHandlePool.execute(new HandleExceptionCallContextWorker(ExceptionRestHandle.EXCEPTION_HANDLE_QUEUE.take()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	class HandleExceptionCallContextWorker implements Runnable {

		private ExceptionRestParam param;

		protected HandleExceptionCallContextWorker(ExceptionRestParam param) {
			this.param = param;
		}

		@Override
		public void run() {
			switch (param.getHandleType()) {
				case HANDLE_TYPE_NULL_POINTER:
					break;
				case HANDLE_TYPE_INDEX_OUT:
					break;
				case HANDLE_TYPE_SQL:
					break;
				case HANDLE_TYPE_DEFAULT:
					break;
			}
		}
	}
}
