package com.obatis.core.annotation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验入参是否为手机号码
 * @author HuangLongPu
 */
@Target({PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsPhoneNumberValidator.class})
public @interface IsPhoneNumber {

    String message() default "手机号码格式不正确";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
