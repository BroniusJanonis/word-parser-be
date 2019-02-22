package com.wordparser.demo.service;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface ConverterMultipartFileService {

    ByteArrayOutputStream toByteArrayOutputStream(@NotNull MultipartFile multipartFile) throws IOException;
}
