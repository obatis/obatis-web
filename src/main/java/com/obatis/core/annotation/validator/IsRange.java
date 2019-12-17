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
 * 校验是否为数字，针对范围，判断是否属于1和2的范围内，value填 "1,2"即可
 * @author HuangLongPu
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsRangeValidator.class})
public @interface IsRange {

	String message() default "参数有误";
	
	String value();
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
