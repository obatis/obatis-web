package com.obatis.core.annotation.validator;

import com.obatis.tools.ValidateTool;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsContactNumberValidator  implements ConstraintValidator<IsContactNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (ValidateTool.isContactNumber(value)) {
            return true;
        }
        return false;
    }
}
