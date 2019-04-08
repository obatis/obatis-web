package com.sbatis.core.annotation.validator;

import com.sbatis.validate.ValidateTool;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsEmailValidator implements ConstraintValidator<IsEmail, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (!ValidateTool.isEmpty(value) && ValidateTool.isEmail(value)) {
			return true;
		}
		return false;
	}

}
