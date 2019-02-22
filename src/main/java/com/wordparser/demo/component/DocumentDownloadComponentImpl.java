package com.wordparser.demo.component;

import com.wordparser.demo.api.DocumentDownloadDTO;
import com.wordparser.demo.service.ParseFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.wordparser.demo.configurator.CacheConfiguration.DOCUMENT_DOWNLOAD_CACHE;

/*
* Problem: not all threads were done while request was returned
* Problem solution source: https://stackoverflow.com/questions/9148899/returning-value-from-thread
 */

@Component
@Transactional(readOnly = true)
public class DocumentDownloadComponentImpl implements DocumentDownloadComponent {

    private FileUploadComponent fileUploadComponent;
    private ParseFileUploadService parseFileUploadService;

    @Autowired
    DocumentDownloadComponentImpl(FileUploadComponent fileUploadComponent, @Qualifier("parseFileUploadServiceImpl") ParseFileUploadService parseFileUploadService) {
        this.fileUploadComponent = fileUploadComponent;
        this.parseFileUploadService = parseFileUploadService;
    }

    @Cacheable(DOCUMENT_DOWNLOAD_CACHE)
    @Override
    public DocumentDownloadDTO getParsed() throws InterruptedException {
        DocumentDownloadDTO dto = new DocumentDownloadDTO();
        List<FileUpload> source = fileUploadComponent.getByContentType(FileContentType.TYPE_TXT);
        CountDownLatch latch = new CountDownLatch(source.size());
        for (FileUpload src : source) {
            parseInSeparateThread(src, dto, latch);
        }
        latch.await();
        return dto;
    }

    private void parseInSeparateThread(FileUpload src, DocumentDownloadDTO dto, CountDownLatch latch) {
        new Thread("" + 1) {
            public void run() {
                parseFileUploadService.parseFileUpload(src, dto);
                latch.countDown();
            }
        }.start();
    }

}
