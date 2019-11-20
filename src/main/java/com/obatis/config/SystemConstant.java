package com.obatis.config;

import com.obatis.config.response.result.callback.ExceptionRestParam;
import org.springframework.core.env.Environment;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class SystemConstant {

    public static final String CORE_BASE_DIR = "com.obatis";
    public static String PROJECT_BASE_DIR = null;
    /**
     * 系统处理队列
     */
    public static final BlockingQueue<ExceptionRestParam> HANDLE_QUEUE = new LinkedBlockingQueue<>();
    /**
     * 系统处理线程池
     */
    public static final ExecutorService HANDLE_POOL = Executors.newFixedThreadPool(2);

    /**
     * springboot 配置属性
     */
    public static Environment ENV;
    /**
     * 服务名称
     */
    public static String SERVICE_NAME;
    /**
     * 运行环境，true 为开发环境，false为生产环境
     */
    public static boolean RUN_DEV_ENV;
}
