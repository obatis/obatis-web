package com.sbatis.core.annotation.validator;

import com.sbatis.convert.CommonConvert;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;

public class LengthValidator implements ConstraintValidator<Length, Object> {

	int min;
	int max;
	
	@Override
	public void initialize(Length length) {
		ConstraintValidator.super.initialize(length);
		min = length.min();
		max = length.max();
	}
	
    @SuppressWarnings("rawtypes")
	@Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
    	
    	if(value instanceof List) {
    		int size = ((List) value).size();
    		if(size < min || size > max) {
    			return false;
    		}
    	} else if(value instanceof Object[]) {
    		int len = ((Object[]) value).length;
    		if(len < min || len > max) {
    			return false;
    		}
    	} else if (value instanceof Map) {
    		int size = ((Map) value).size();
    		if(size < min || size > max) {
    			return false;
    		}
    	} else {
    		int len = CommonConvert.toString(value).length();
    		if(len < min || len > max) {
    			return false;
    		}
    	}
        
        return true;
    }

}
