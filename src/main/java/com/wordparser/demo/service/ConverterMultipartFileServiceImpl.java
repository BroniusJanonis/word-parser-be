package com.wordparser.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ConverterMultipartFileServiceImpl implements ConverterMultipartFileService {
    @Override
    public ByteArrayOutputStream toByteArrayOutputStream(@NotNull MultipartFile multipartFile) throws IOException {
        InputStream is = multipartFile.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer;
    }
}
