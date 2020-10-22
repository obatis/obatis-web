package com.obatis.core.annotation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证是否为车牌号
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { IsVehicleLicenseValidator.class })
public @interface IsVehicleLicense {

    String message() default "不是有效的车牌号";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
