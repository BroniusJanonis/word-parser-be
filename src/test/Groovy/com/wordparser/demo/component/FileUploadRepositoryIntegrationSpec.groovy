package com.wordparser.demo.component


import com.wordparser.demo.SpecIntegration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime

@DataJpaTest
class FileUploadRepositoryIntegrationSpec extends SpecIntegration{

    private static final String SOURCE_FILE_UPLOAD_DOCX1 = "src/test/resources/parser-text-0.txt"
    private static final String SOURCE_FILE_UPLOAD_DOCX2 = "src/test/resources/parser-text-1.txt"

    @Autowired
    FileUploadRepository repository

    def "when saving list of FileUpload then it saves"() {
        given:
            FileUpload fileUpload1 = new FileUpload()
            byte[] inputStream1 = Files.readAllBytes(Paths.get(SOURCE_FILE_UPLOAD_DOCX1))
            fileUpload1.setFileContent(inputStream1)
            fileUpload1.setFileName("filename1")
            fileUpload1.setUploadedOn(LocalDateTime.now())
        and:
            FileUpload fileUpload2 = new FileUpload()
            byte[] inputStream2 = Files.readAllBytes(Paths.get(SOURCE_FILE_UPLOAD_DOCX2))
            fileUpload2.setFileContent(inputStream2)
            fileUpload2.setFileName("filename2")
            fileUpload2.setUploadedOn(LocalDateTime.now().plusSeconds(5L))
        and:
            List<FileUpload> fileUploads = [fileUpload1, fileUpload2]
        when:
            List<FileUpload> response = repository.save(fileUploads)
        then:
            assert response.size() == fileUploads.size()
    }

    def "given FileUpload list when getting then it gets"() {
        given:
            FileUpload fileUpload1 = new FileUpload()
            byte[] inputStream1 = Files.readAllBytes(Paths.get(SOURCE_FILE_UPLOAD_DOCX1))
            fileUpload1.setFileContent(inputStream1)
            fileUpload1.setFileName("filename1")
            fileUpload1.setUploadedOn(LocalDateTime.now())
        and:
            FileUpload fileUpload2 = new FileUpload()
            byte[] inputStream2 = Files.readAllBytes(Paths.get(SOURCE_FILE_UPLOAD_DOCX2))
            fileUpload2.setFileContent(inputStream2)
            fileUpload2.setFileName("filename2")
            fileUpload2.setUploadedOn(LocalDateTime.now().plusSeconds(5L))
        and:
            List<FileUpload> fileUploads = [fileUpload1, fileUpload2]
        when:
            List<FileUpload> fileUploadsSaved = repository.save(fileUploads)
        then:
            assert fileUploadsSaved.size() == 2
        when:
            List<FileUpload> fileUploadsList = repository.findAll()
        then:
            assert fileUploadsList.size() == 2
    }
}
