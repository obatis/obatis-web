package com.sbatis.core.annotation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验是否为订单号格式
 * @author HuangLongPu
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { IsOrderNoValidator.class })
public @interface IsOrderNo {

	String message() default "订单号只能包含数字、字母、下划线和中划线";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
