package com.sbatis.core.annotation.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 针对数字范围的校验，比如判断是否属于1和2的范围内，value值填 "1,2"即可验证
 * @author HuangLongPu
 *
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {NumberRangeValidator.class})
public @interface NumberRange {

	String message() default "参数有误";
	
	String value();
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
