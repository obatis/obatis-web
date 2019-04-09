package com.sbatis.core.annotation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 排序方式校验，只能接0:升序 1:降序
 * @author HuangLongPu
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {OrderValidValidator.class})
public @interface OrderValid {

	String message() default "参数值错误";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
