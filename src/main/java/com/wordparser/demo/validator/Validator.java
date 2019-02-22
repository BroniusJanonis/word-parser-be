package com.wordparser.demo.validator;

import java.util.ArrayList;
import java.util.List;

public class Validator<T> implements ValidationRule<T> {
    @Override
    public boolean isDependant() {
        return false;
    }

    private List<ValidationRule<T>> tests = new ArrayList<ValidationRule<T>>();

    public Validator(ValidationStrategy<T> type) {
        this.tests = type.getRules();
    }

    public void addRule(ValidationRule<T> rule) {
        tests.add(rule);
    }

    public List<ValidationError> check(T value) {
        List<ValidationError> list = new ArrayList<ValidationError>();
        for (ValidationRule<T> rule : tests) {
            if (!list.isEmpty() && rule.isDependant()) {
                continue;
            }
            list.addAll(rule.check(value));
        }
        return list;
    }
}
