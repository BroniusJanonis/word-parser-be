package com.wordparser.demo.component.validation.rules;

import com.wordparser.demo.component.validation.Errors;
import com.wordparser.demo.validator.ValidationError;
import com.wordparser.demo.validator.ValidationRule;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class FileSizeExceeds1MbRule implements ValidationRule<List<MultipartFile>> {

    private static final long FILE_SIZE_1_MB_LIMIT = 1024L * 1024L;

    @Override
    public boolean isDependant() {
        return false;
    }

    @Override
    public List<ValidationError> check(List<MultipartFile> value) {
        List<ValidationError> errors = new ArrayList<>();
        for (MultipartFile file: value){
            if(file.getSize() > FILE_SIZE_1_MB_LIMIT){
                errors.add(new Errors.MultipartFileSizeExceeds1Mb(file.getSize()));
            }
        }
        return errors;
    }
}
