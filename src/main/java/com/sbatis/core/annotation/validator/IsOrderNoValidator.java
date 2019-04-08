package com.sbatis.core.annotation.validator;

import com.sbatis.validate.ValidateTool;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsOrderNoValidator implements ConstraintValidator<IsOrderNo, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		return !ValidateTool.isEmpty(value) && value.toString().matches("[0-9A-Za-z_-]*");
	}
	
}
