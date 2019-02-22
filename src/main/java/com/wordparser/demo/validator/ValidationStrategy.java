package com.wordparser.demo.validator;

import java.util.List;

public interface ValidationStrategy<T> {
    List<ValidationRule<T>> getRules();
}