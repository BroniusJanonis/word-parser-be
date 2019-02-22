package com.wordparser.demo.component;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface FileUploadComponent {

    List<FileUpload> getByContentType(@NotNull FileContentType contentType);

    List<FileUpload> saveAll(@NotEmpty List<FileUpload> fileUploads);

    boolean deleteAllByContentType(@NotBlank String contentType);

}
