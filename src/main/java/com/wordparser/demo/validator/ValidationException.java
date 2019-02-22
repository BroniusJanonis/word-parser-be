package com.wordparser.demo.validator;

import java.util.List;
import java.util.function.Supplier;

public class ValidationException extends RuntimeException {

    private List<ValidationError> errors;

    public ValidationException(List<ValidationError> errors) {
        super("Validation errors occurred. ErrorCodes: " + errors.toString());
        this.errors = errors;
    }

    public static Supplier<ValidationException> supplier(List<ValidationError> errors) {
        return () -> new ValidationException(errors);
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
