package com.obatis.core.annotation.request;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 由于框架配置的原因，所有请求的接口都会进行统一结构化处理，此注解用于请求的 api 方法体。
 * 使用了此注解的方法，返回结果将会原样输出，不再进行统一结构化处理
 */
@Target({ METHOD })
@Retention(RUNTIME)
@Documented
public @interface ReturnTypeValue {
}
