package com.sbatis.core.annotation.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 校验入参是否为手机号码
 * @author HuangLongPu
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsPhoneNumberValidator.class})
public @interface IsPhoneNumber {

String message() default "手机号码格式不正确";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
