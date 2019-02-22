package com.wordparser.demo.api.configuration;


import com.wordparser.demo.api.ErrorDTO;
import com.wordparser.demo.validator.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
class GlobalControllerExceptionHandler {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<ErrorDTO> handle(ValidationException e) {
        log.error(String.format("Invoked exception handler for %s", e.getClass()), e);
        return e.getErrors().stream().map(error -> {
            return new ErrorDTO(error.getErrorCode(), error.getErrorMsg());
        }).collect(Collectors.toList());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO handle(Exception e) {
        return handleDefault(e);
    }

    private ErrorDTO handleDefault(Exception e) {
        log.error(String.format("Invoked exception handler for %s", e.getClass()), e);
        return new ErrorDTO(ApiErrorCodes.UNEXPECTED, "Unexpected error occurred");
    }
}
