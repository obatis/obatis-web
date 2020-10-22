package com.obatis.core.annotation.validator;

import com.obatis.tools.ValidateTool;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsVehicleLicenseValidator implements ConstraintValidator<IsVehicleLicense, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (ValidateTool.isVehicleLicense(value)) {
            return true;
        }
        return false;
    }
}
