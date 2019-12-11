package com.obatis.core.annotation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验长度，包括最大值和最小值
 * @author HuangLongPu
 */
@Target({PARAMETER})
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
