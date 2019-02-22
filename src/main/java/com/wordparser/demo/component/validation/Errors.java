package com.wordparser.demo.component.validation;

import com.wordparser.demo.validator.ValidationError;

import java.text.MessageFormat;

public class Errors {

    public static class MultipartFileContentTypeInvalid extends ValidationError {
        public static String code = "MULTIPART_FILE_CONTENT_TYPE_INVALID";
        public MultipartFileContentTypeInvalid(String contentType, String allowedContentType) {
            super(code, MessageFormat.format("File uploaded type is [{0}] but can only be [{1}] type ", contentType, allowedContentType));
        }
    }

    public static class NoMultipartFileIsPresent extends ValidationError {
        public static String code = "NO_MULTIPART_FILE_IS_PRESENT";
        public NoMultipartFileIsPresent() {
            super(code, "No multipart file was attached to the request");
        }
    }

    public static class MultipartFileSizeExceeds1Mb extends ValidationError {
        public static String code = "MULTIPART_FILE_SIZE_EXCEEDS_1_MB";
        public MultipartFileSizeExceeds1Mb(long uploaddedFileSize) {
            super(code, MessageFormat.format("Uploaded multipart file size [{0}] bytes exceeds 1 mb ", uploaddedFileSize));
        }
    }

}
