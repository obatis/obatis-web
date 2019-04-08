package com.sbatis.core.annotation.validator;

import com.sbatis.validate.ValidateTool;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * description: 验证实现类
 */
public class NotZeroValidator implements ConstraintValidator<NotZero, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
    	if(ValidateTool.isEmpty(value)) {
    		return false;
    	} else if("0".equals(value.toString())) {
    		return false;
    	}
        
        return true;
    }
}
