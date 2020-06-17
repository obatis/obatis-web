package com.obatis.core.annotation.validator;

import com.obatis.tools.ValidateTool;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 *
 * description: 验证实现类
 */
public class NotZeroValidator implements ConstraintValidator<NotZero, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
    	if(ValidateTool.isEmpty(value)) {
    		return false;
    	}
        return !ValidateTool.isZero(value);
    }
}
