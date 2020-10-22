package com.obatis.core.annotation.validator;

import com.obatis.tools.ValidateTool;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsEmailValidator implements ConstraintValidator<IsEmail, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (ValidateTool.isEmail(value)) {
			return true;
		}
		return false;
	}

}
