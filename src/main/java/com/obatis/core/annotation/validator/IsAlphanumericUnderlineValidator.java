package com.obatis.core.annotation.validator;

import com.obatis.tools.ValidateTool;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsAlphanumericUnderlineValidator implements ConstraintValidator<IsAlphanumericUnderline, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		return ValidateTool.isAlphanumericUnderline(value);
	}
	
}
