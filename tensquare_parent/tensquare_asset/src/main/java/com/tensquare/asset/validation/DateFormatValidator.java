package com.tensquare.asset.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;

public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {
    private String format;
    private boolean nullable;

    public DateFormatValidator() {
    }

    public void initialize(DateFormat constraintAnnotation) {
        this.format = constraintAnnotation.format();
        this.nullable = constraintAnnotation.nullable();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (this.nullable && null == value) {
            return true;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(this.format);

            try {
                sdf.parse(value);
                return true;
            } catch (Exception var5) {
                return false;
            }
        }
    }
}