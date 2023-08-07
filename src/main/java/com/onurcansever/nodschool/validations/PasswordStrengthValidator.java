package com.onurcansever.nodschool.validations;

import com.onurcansever.nodschool.annotations.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordValidator, String> {

    private List<String> weakPasswords;

    @Override
    public void initialize(PasswordValidator constraintAnnotation) {
        this.weakPasswords = Arrays.asList("12345", "password", "qwerty");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !weakPasswords.contains(value);
    }
}
