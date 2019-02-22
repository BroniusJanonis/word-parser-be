package com.wordparser.demo.component


import com.wordparser.demo.Spec
import com.wordparser.demo.api.DocumentDownloadDTO
import com.wordparser.demo.service.ParseFileUploadServiceImpl
import org.junit.Test
import spock.lang.Subject

import java.nio.file.Files
import java.nio.file.Paths

class ParseFileUploadServiceImplSpec extends Spec{

    @Subject
    private ParseFileUploadServiceImpl service

    def setup(){
        service = new ParseFileUploadServiceImpl()
    }

    @Test()
    def "givenFileUpload_whenProcessing_thenNoExceptionsThrown"(){
        given:
            byte[] inputStream = Files.readAllBytes(Paths.get(SOURCE_FILE_UPLOAD_TXT0))
            FileUpload fileUpload = new FileUpload()
            fileUpload.setFileContent(inputStream)
        and:
        DocumentDownloadDTO dto = new DocumentDownloadDTO()
        when:
            service.parseFileUpload(fileUpload, dto)
        then:
            noExceptionThrown()
    }

    @Test()
    def "givenFileUploadWithEmptyInputStream_whenProcessing_thenNoExceptionsThrown"(){
        given:
            byte[] inputStream = new byte[0]
            FileUpload fileUpload = new FileUpload()
        fileUpload.setFileContent(inputStream)
        and:
            DocumentDownloadDTO dto = new DocumentDownloadDTO()
        when:
            service.parseFileUpload(fileUpload, dto)
        then:
            noExceptionThrown()
    }

}
