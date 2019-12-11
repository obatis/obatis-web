package com.obatis.core.annotation.request;

import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * api 接口返回数据时，加密参数返回
 * 该注解尚未实现完毕，暂不提供使用
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Documented
public @interface ParamEncrypt {

    String method() default "电子邮箱地址不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
