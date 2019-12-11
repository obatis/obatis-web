package com.obatis.core.annotation.request;

import org.springframework.cloud.openfeign.FeignClient;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 微服务 Feign调用封装，自动实现熔断机制参数返回，无效强制方法实现
 * 该注解尚未实现完毕，暂不提供使用
 * @author HuangLongPu
 */
@Target({ METHOD })
@Retention(RUNTIME)
@Documented
@FeignClient
public @interface ConfigFeignClient {

    /**
     * 服务名称
     * @return
     */
    String path();
}
