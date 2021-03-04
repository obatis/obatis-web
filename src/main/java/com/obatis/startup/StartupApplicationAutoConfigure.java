package com.obatis.startup;

import com.obatis.config.SystemConstant;
import com.obatis.config.response.ResponseResultHandleFactory;
import com.obatis.config.response.result.callback.ExceptionRestHandle;
import com.obatis.config.response.result.callback.ExceptionRestHandleCallback;
import com.obatis.config.response.result.callback.HandleExceptionCallbackContext;
import com.obatis.config.url.RegisterUrlConfigure;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.Resource;
import java.util.Map;

@Configuration
public class StartupApplicationAutoConfigure implements ApplicationRunner {

    @Resource
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private Environment environment;

    @Bean
    public int loadSystemEnv() {
        SystemConstant.setSystemEnv(environment);
        return 0;
    }

    /**
     * 该方法在项目启动时运行
     */
    @Override
    public void run(ApplicationArguments args) {

        // 加载返回封装
        ResponseResultHandleFactory.handleResponseResultInfo(requestMappingHandlerAdapter);

        /**
         * 处理异常回调
         */
        this.handleExceptionCall();
        /**
         * 处理 Controller url
         */
        this.handleMappingBean();
        /**
         * 处理接口信息注册
         */
        this.registerUrlCall();
    }

    /**
     * 异常回调处理
     */
    private void handleExceptionCall() {
        Map<String, ExceptionRestHandleCallback> beanMap = applicationContext.getBeansOfType(ExceptionRestHandleCallback.class);
        if (beanMap != null && !beanMap.isEmpty()) {
            ExceptionRestHandle.ADD_EXCEPTION_FLAG = true;
            SystemConstant.HANDLE_POOL.execute(new HandleExceptionCallbackContext(beanMap));
        }

    }

    /**
     * 处理 Controller url
     */
    private void handleMappingBean() {
        new MappingBeanMethodHandle().load(applicationContext);
    }

    /**
     * 处理接口信息注册
     */
    private void registerUrlCall() {
        Map<String, RegisterUrlConfigure> beanMap = applicationContext.getBeansOfType(RegisterUrlConfigure.class);
        if (beanMap != null && !beanMap.isEmpty()) {
            for (Map.Entry<String, RegisterUrlConfigure> value : beanMap.entrySet()) {
                // 调用注册URL地址信息
                if (MappingBeanHandleAutoConfigure.getUrlInfo() != null && !MappingBeanHandleAutoConfigure.getUrlInfo().isEmpty()) {
                    value.getValue().registerUrl(MappingBeanHandleAutoConfigure.getUrlInfo());
                }
                // 调用注册无需登录的URL地址信息
                if (MappingBeanUrlNotLoginMethodHandle.getNotLoginUrl() != null && !MappingBeanUrlNotLoginMethodHandle.getNotLoginUrl().isEmpty()) {
                    value.getValue().registerNotLoginUrl(MappingBeanUrlNotLoginMethodHandle.getNotLoginUrl());
                }
            }
        }
    }

}
