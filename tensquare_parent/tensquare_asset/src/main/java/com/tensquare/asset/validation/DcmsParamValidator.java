package com.tensquare.asset.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

public class DcmsParamValidator {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public DcmsParamValidator() {
    }

    public static <T> ValidationResult validate(T object, Class... groups) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> violations = validator.validate(object, groups);
        Iterator<ConstraintViolation<T>> iter = violations.iterator();
        if (iter.hasNext()) {
            String errMessage = ((ConstraintViolation)iter.next()).getMessage();
            result.setSuccess(false);
            result.setErrorMsg(errMessage);
            return result;
        } else {
            return result;
        }
    }
}
