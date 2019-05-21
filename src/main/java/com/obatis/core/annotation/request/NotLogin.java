package com.obatis.core.annotation.request;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * api 接口不需要登录注解
 */
@Target({ METHOD })
@Retention(RUNTIME)
@Documented
public @interface NotLogin {
}
