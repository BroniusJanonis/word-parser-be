package com.wordparser.demo.component.validation.rules;

import com.wordparser.demo.component.FileContentType;
import com.wordparser.demo.component.validation.Errors;
import com.wordparser.demo.validator.ValidationError;
import com.wordparser.demo.validator.ValidationRule;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class FileUploadTypeValidRule implements ValidationRule<List<MultipartFile>> {

    private static final String FILE_TYPE_NOT_PRESENT = "file type is not present";

    @Override
    public boolean isDependant() {
        return false;
    }

    @Override
    public List<ValidationError> check(List<MultipartFile> value) {
        List<ValidationError> errors = new ArrayList<>();
        for(MultipartFile file: value){
            String contentType = !file.getContentType().isEmpty() ? file.getContentType() : FILE_TYPE_NOT_PRESENT;
            if(!FileContentType.TYPE_TXT.toString().equals(contentType)){
                errors.add(new Errors.MultipartFileContentTypeInvalid(contentType, FileContentType.TYPE_TXT.toString()));
            }
        }
        return errors;
    }
}
