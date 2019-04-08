package com.sbatis.core.annotation.validator;

import com.sbatis.validate.ValidateTool;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsPhoneNumberValidator implements ConstraintValidator<IsPhoneNumber, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (!ValidateTool.isEmpty(value) && ValidateTool.isPhoneNumber(value)) {
			return true;
		}
		return false;
	}

}
