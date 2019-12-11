package com.obatis.core.annotation.request;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * api 接口不需要进行签名验证注解，添加此注解的 Controller 方法，表示无需进行签名验证
 * 此方法默认规则：
 * 1、如果接口不需要登录，则不进行签名验证（默认只有需要登录的接口，才进行签名验证），如果不需要默认，设置 defualtValidate 属性为false即可;
 * 该注解尚未实现完毕，暂不提供使用
 * @author HuangLongPu
 */
@Target({ METHOD })
@Retention(RUNTIME)
@Documented
public @interface NotValidateSign {

    boolean defualtValidate() default true;
}
