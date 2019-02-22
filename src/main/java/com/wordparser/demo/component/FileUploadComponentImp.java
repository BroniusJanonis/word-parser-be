package com.wordparser.demo.component;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.wordparser.demo.configurator.CacheConfiguration.DOCUMENT_DOWNLOAD_CACHE;

@Component
@Transactional(readOnly = true)
public class FileUploadComponentImp implements FileUploadComponent {

    private FileUploadRepository repository;

    @Autowired
    FileUploadComponentImp(FileUploadRepository repository){
        this.repository = repository;
    }

    @Override
    public List<FileUpload> getByContentType(FileContentType contentType) {
        return repository.getByContentType(contentType);
    }

    @CacheEvict(value = DOCUMENT_DOWNLOAD_CACHE, allEntries = true)
    @Override
    @Transactional
    public List<FileUpload> saveAll(@NotEmpty List<FileUpload> fileUploads){
        return repository.save(fileUploads);
    }

    @CacheEvict(value = DOCUMENT_DOWNLOAD_CACHE, allEntries = true)
    @Override
    @Transactional
    public boolean deleteAllByContentType(@NotBlank String contentType) {
        if(FileContentType.TYPE_TXT.toString().contains(contentType)){
            return repository.deleteByContentType(FileContentType.TYPE_TXT) > 0;
        }
        return false;
    }
}
