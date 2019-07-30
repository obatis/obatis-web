package com.obatis.core.annotation.validator;

import com.obatis.validate.ValidateTool;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.math.BigInteger;

public class MaxValueValidator implements ConstraintValidator<MaxValue, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if(ValidateTool.isEmpty(o)) {
            return false;
        }
        if(o instanceof Integer) {

        } else if (o instanceof  Long) {

        } else if (o instanceof Float) {

        } else if (o instanceof Double) {

        } else if (o instanceof BigInteger) {

        } else if (o instanceof BigDecimal) {

        }
        return false;
    }
}
