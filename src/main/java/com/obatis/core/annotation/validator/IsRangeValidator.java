package com.obatis.core.annotation.validator;

import com.obatis.convert.CommonConvert;
import com.obatis.validate.ValidateTool;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsRangeValidator implements ConstraintValidator<IsRange, Object> {

	String numberValue = null;
	
	@Override
	public void initialize(IsRange numberRange) {
		ConstraintValidator.super.initialize(numberRange);
		numberValue = numberRange.value();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		if(ValidateTool.isEmpty(value)) {
			return false;
		} else if (ValidateTool.isEmpty(numberValue)) {
			return false;
		}
		String[] numberArr = numberValue.split(",");
		for (String number : numberArr) {
			if(CommonConvert.toString(value).equals(number)) {
				return true;
			}
		}
		return false;
	}

}
