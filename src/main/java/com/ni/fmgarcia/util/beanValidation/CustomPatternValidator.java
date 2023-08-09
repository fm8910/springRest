package com.ni.fmgarcia.util.beanValidation;

import jakarta.validation.ConstraintValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class CustomPatternValidator implements ConstraintValidator<CustomPattern, String> {

    @Autowired
    private Environment environment;

    private String customRegex;

    @Override
    public void initialize(CustomPattern customPattern) {
        this.customRegex = customPattern.regexp();
    }

    @Override
    public boolean isValid(String value, jakarta.validation.ConstraintValidatorContext context) {
        String pattern = environment.getProperty(customRegex);
        return value != null && value.matches(pattern);
    }
}
