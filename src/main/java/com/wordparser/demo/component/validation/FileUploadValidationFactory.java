package com.wordparser.demo.component.validation;

import com.google.common.collect.ImmutableList;
import com.wordparser.demo.component.validation.rules.FileSizeExceeds1MbRule;
import com.wordparser.demo.component.validation.rules.FileUploadTypeValidRule;
import com.wordparser.demo.component.validation.rules.NoFileUploadIsPresentRule;
import com.wordparser.demo.validator.Validator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileUploadValidationFactory {

    public Validator<List<MultipartFile>> getFileUploadValidator() {
        return new Validator<>(() ->
                ImmutableList.of(
                        new FileUploadTypeValidRule(),
                        new NoFileUploadIsPresentRule(),
                        new FileSizeExceeds1MbRule()));
    }

}
