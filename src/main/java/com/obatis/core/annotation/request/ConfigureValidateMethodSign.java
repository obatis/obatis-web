package com.obatis.core.annotation.request;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 该注解尚未实现完毕，暂不提供使用
 */
@Target({ TYPE })
@Retention(RUNTIME)
@Documented
public @interface ConfigureValidateMethodSign {
}
