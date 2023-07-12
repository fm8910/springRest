package com.ni.fmgarcia.util.validation;

import com.ni.fmgarcia.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userService.findUserByEmail(email).isEmpty();
    }
}
