package com.obatis.core.annotation.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 校验长度，包括最大值和最小值
 * @author HuangLongPu
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {LengthValidator.class})
public @interface Length {
	
	int min();
	
	int max();

	String message() default "无效值";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
