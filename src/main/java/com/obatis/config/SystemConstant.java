package com.obatis.config;

import com.obatis.config.response.result.callback.ExceptionRestParam;
import com.obatis.tools.ValidateTool;
import org.springframework.core.env.Environment;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public final class SystemConstant {

    public static final String CORE_BASE_DIR = "com.obatis";
    public static String PROJECT_BASE_DIR = null;
    /**
     * 系统处理队列
     */
    public static final BlockingQueue<ExceptionRestParam> HANDLE_QUEUE = new LinkedBlockingQueue<>();
    /**
     * 系统处理线程池
     */
    public static final ExecutorService HANDLE_POOL = Executors.newFixedThreadPool(3);

    /**
     * springboot 配置属性
     */
    private static Environment ENV;
    /**
     * 服务名称
     */
    public static String SERVICE_NAME;
    /**
     * 运行环境，true 为开发环境，false为生产环境
     */
    private static boolean RUN_DEV_ENV;

    public static synchronized void setSystemEnv(Environment environment) {
        if(ENV == null) {
            ENV = environment;

            SERVICE_NAME = environment.getProperty("spring.application.name");
            String runEvn =  environment.getProperty("spring.profiles.active");
            if(ValidateTool.isEmpty(runEvn) || runEvn.toLowerCase().equals("dev")) {
                RUN_DEV_ENV = true;
            }
        }
    }

    /**
     * 获取环境配置
     * @return
     */
    public static Environment getSystemEnv() {
        return ENV;
    }

    /**
     * 获取运行环境是否为开发环境，true表示开发环境，false表示不为开发环境
     * @return
     */
    public static boolean getRunDevEnv() {
        return RUN_DEV_ENV;
    }
}
