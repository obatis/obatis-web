package com.obatis.config;

import org.springframework.core.env.Environment;

public class SystemConstant {

    public static final String CORE_BASE_DIR = "com.obatis";
    public static String PROJECT_BASE_DIR = null;

    /**
     * springboot 配置属性
     */
    public static Environment ENV;
    /**
     * 服务名称
     */
    public static String SERVICE_NAME;
}
