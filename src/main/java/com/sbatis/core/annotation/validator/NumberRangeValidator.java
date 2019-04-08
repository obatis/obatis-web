package com.sbatis.core.annotation.validator;

import com.sbatis.validate.ValidateTool;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberRangeValidator implements ConstraintValidator<NumberRange, Integer> {

	String numberValue = null;
	
	@Override
	public void initialize(NumberRange numberRange) {
		ConstraintValidator.super.initialize(numberRange);
		numberValue = numberRange.value();
	}
	
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		
		if(ValidateTool.isEmpty(value)) {
			return false;
		} else if (ValidateTool.isEmpty(numberValue)) {
			return false;
		}
		String[] numberArr = numberValue.split(",");
		for (String number : numberArr) {
			if(value.toString().equals(number)) {
				return true;
			}
		}
		return false;
	}

}
