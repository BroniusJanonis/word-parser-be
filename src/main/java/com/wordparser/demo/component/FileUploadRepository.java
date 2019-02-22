package com.wordparser.demo.component;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, String> {

    List<FileUpload> getByContentType(@NotNull FileContentType contentType);

    int deleteByContentType(@NotNull FileContentType contentType);
}
