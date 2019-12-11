package com.obatis.core.annotation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验不能为0，包括空值
 * @author HuangLongPu
 */
@Target({PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {NotZeroValidator.class})
public @interface NotZero {

    String message() default "不能为0";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
