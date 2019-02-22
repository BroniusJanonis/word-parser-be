package com.wordparser.demo.service;

import com.wordparser.demo.api.DocumentDownloadDTO;
import com.wordparser.demo.component.FileUpload;

import javax.validation.constraints.NotNull;

public interface ParseFileUploadService {

    void parseFileUpload(@NotNull FileUpload src, @NotNull DocumentDownloadDTO documentDownloadDTO);

}
