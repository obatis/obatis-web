package com.obatis.core.annotation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验是否为电子邮箱地址
 * @author HuangLongPu
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { IsEmailValidator.class })
public @interface IsEmail {

	String message() default "电子邮箱地址不正确";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
