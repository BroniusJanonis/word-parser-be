package com.wordparser.demo.component.validation.rules;

import com.wordparser.demo.component.validation.Errors;
import com.wordparser.demo.validator.ValidationError;
import com.wordparser.demo.validator.ValidationRule;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class NoFileUploadIsPresentRule implements ValidationRule<List<MultipartFile>> {

    @Override
    public boolean isDependant() {
        return false;
    }

    @Override
    public List<ValidationError> check(List<MultipartFile> value) {
        List<ValidationError> errors = new ArrayList<>();
        if(value.isEmpty()){
            errors.add(new Errors.NoMultipartFileIsPresent());
        }
        return errors;
    }
}
