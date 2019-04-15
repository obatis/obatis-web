package com.obatis.core.annotation.validator;

import com.obatis.validate.ValidateTool;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsNoValidator implements ConstraintValidator<IsNo, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		return !ValidateTool.isEmpty(value) && value.toString().matches("[0-9A-Za-z_-]*");
	}
	
}
