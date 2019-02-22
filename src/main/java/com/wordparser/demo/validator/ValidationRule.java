package com.wordparser.demo.validator;

import java.util.List;

public interface ValidationRule<T> {
    boolean isDependant();

    List<ValidationError> check(T value);
}
