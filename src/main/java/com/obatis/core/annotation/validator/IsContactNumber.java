package com.obatis.core.annotation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验是否为有效的联系电话(用于校验是手机号码和座机号码)
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { IsContactNumberValidator.class })
public @interface IsContactNumber {

    String message() default "不是有效的联系电话";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
