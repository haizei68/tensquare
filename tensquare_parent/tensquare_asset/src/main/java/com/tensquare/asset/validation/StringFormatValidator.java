package com.tensquare.asset.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringFormatValidator implements ConstraintValidator<StringFormat, String> {
    private int min;
    private int max;
    private boolean nullable;
    private String regex = null;

    public StringFormatValidator() {
    }

    public void initialize(StringFormat constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.nullable = constraintAnnotation.nullable();
        this.regex = constraintAnnotation.regex();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value && !this.nullable) {
            return false;
        } else {
            if (null != value) {
                if (this.min >= 0 && value.length() < this.min) {
                    return false;
                }

                if (this.max >= 0 && value.length() > this.max) {
                    return false;
                }

                if (this.regex != null && this.regex.length() > 0 && !value.matches(this.regex)) {
                    return false;
                }
            }

            return true;
        }
    }
}
