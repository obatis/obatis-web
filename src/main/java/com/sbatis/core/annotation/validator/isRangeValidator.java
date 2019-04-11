package com.sbatis.core.annotation.validator;

import com.sbatis.convert.CommonConvert;
import com.sbatis.validate.ValidateTool;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class isRangeValidator implements ConstraintValidator<isRange, Object> {

	String numberValue = null;
	
	@Override
	public void initialize(isRange numberRange) {
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
