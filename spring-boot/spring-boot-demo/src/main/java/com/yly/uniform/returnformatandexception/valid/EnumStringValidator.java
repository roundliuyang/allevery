package com.yly.uniform.returnformatandexception.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * @author jam
 * @date 2021/8/4 4:30 下午
 */
public class EnumStringValidator implements ConstraintValidator<EnumString, String> {
    private List<String> enumStringList;

    @Override
    public void initialize(EnumString constraintAnnotation) {
        enumStringList = Arrays.asList(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        return enumStringList.contains(value);
    }
}

