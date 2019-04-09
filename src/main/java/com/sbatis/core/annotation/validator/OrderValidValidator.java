package com.sbatis.core.annotation.validator;

import com.sbatis.config.request.RequestConstant;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 排序值校验实现类
 * @author HuangLongPu
 */
public class OrderValidValidator implements ConstraintValidator<OrderValid, Integer> {
	
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (null == value) {
            return true;
        }
        if (RequestConstant.ORDER_ASC != value && RequestConstant.ORDER_DESC != value) {
            return false;
        }
        return true;
    }
}
