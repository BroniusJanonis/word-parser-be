package com.wordparser.demo.api

import com.fasterxml.jackson.core.type.TypeReference
import com.wordparser.demo.SpecIntegration
import com.wordparser.demo.api.configuration.WordParserMediaType
import com.wordparser.demo.component.FileContentType
import com.wordparser.demo.component.FileUploadComponent
import com.wordparser.demo.component.FileUpload
import com.wordparser.demo.component.validation.Errors
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class FileUploadControllerIntegrationSpec extends SpecIntegration{

    @Autowired
    MockMvc mvc
    @Autowired
    private FileUploadComponent fileUploadComponent

    def "given txt file list when saving then it is saved"(){
        given:
            InputStream resourceAsStream0 = new FileInputStream(SOURCE_FILE_UPLOAD_TXT0)
            MockMultipartFile upload0 = new MockMultipartFile("files", "uploadedFile0.txt", TXT_CONTENT_TYPE, resourceAsStream0)
        and:
            InputStream resourceAsStream1 = new FileInputStream(SOURCE_FILE_UPLOAD_TXT1)
            MockMultipartFile upload1 = new MockMultipartFile("files", "uploadedFile1.txt", TXT_CONTENT_TYPE, resourceAsStream1)
        and:
            int fileSize = [upload0, upload1].size()
        when:
        String response = mvc.perform(MockMvcRequestBuilders.fileUpload(FileUploadController.ROOT_MAPPING)
                .file(upload0)
                .file(upload1)
                .header("Content-Type", "multipart/form-data"))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString()
        then:
            List<FileUpload> fileUploads = objectMapper.readValue(response, new TypeReference<List<FileUpload>>() {})
            assert fileUploads.size() == fileSize
    }

    def "given no file when saving then no file exeption thrown"(){
        given:
            def error = new Errors.NoMultipartFileIsPresent()
        when:
            mvc.perform(MockMvcRequestBuilders.fileUpload(FileUploadController.ROOT_MAPPING)
                    .header("Content-Type", "multipart/form-data"))
                    .andExpect(status().is4xxClientError())
                    .andExpect(jsonPath('$[0].errorCode').value(error.getErrorCode()))
                    .andExpect(jsonPath('$[0].errorMsg').value(error.getErrorMsg()))
        then:
            noExceptionThrown()
    }

    def "given content type file when saving then MULTIPLE_FILE_CONTENT_INVALID error thrown"(){
        given:
            String invalidContentType = "INVALID_CONTENT_TYPE"
            String allowdContentType = FileContentType.TYPE_TXT.toString()
            def error = new Errors.MultipartFileContentTypeInvalid(invalidContentType, allowdContentType)
            InputStream resourceAsStream0 = new FileInputStream(SOURCE_FILE_UPLOAD_DOCX2)
            MockMultipartFile upload0 = new MockMultipartFile("files", "uploadedFile0.docx", invalidContentType, resourceAsStream0)
        when:
            mvc.perform(MockMvcRequestBuilders.fileUpload(FileUploadController.ROOT_MAPPING)
                    .file(upload0)
                    .header("Content-Type", "multipart/form-data"))
                    .andExpect(status().is4xxClientError())
                    .andExpect(jsonPath('$[0].errorCode').value(error.getErrorCode()))
                    .andExpect(jsonPath('$[0].errorMsg').value(error.getErrorMsg()))
        then:
            noExceptionThrown()
    }

    def "given txt file with 1.2 MB size when saving then SIZE_LIMIT_EXCEEDED error thrown"(){
        given:
            InputStream resourceAsStream0 = new FileInputStream(SOURCE_FILE_UPLOAD_DOCX2)
            MockMultipartFile upload0 = new MockMultipartFile("files", "uploadedFile0.txt", TXT_CONTENT_TYPE, resourceAsStream0)
            def error = new Errors.MultipartFileSizeExceeds1Mb(upload0.getSize())
        when:
            mvc.perform(MockMvcRequestBuilders.fileUpload(FileUploadController.ROOT_MAPPING)
                    .file(upload0)
                    .header("Content-Type", "multipart/form-data"))
                    .andExpect(status().is4xxClientError())
                    .andExpect(jsonPath('$[0].errorCode').value(error.getErrorCode()))
                    .andExpect(jsonPath('$[0].errorMsg').value(error.getErrorMsg()))
        then:
            noExceptionThrown()
    }

    def "given saved txt files when deleting all then it deletes"() {
        given:
            InputStream resourceAsStream0 = new FileInputStream(SOURCE_FILE_UPLOAD_TXT0)
            MockMultipartFile upload0 = new MockMultipartFile("files", "uploadedFile0.txt", TXT_CONTENT_TYPE, resourceAsStream0)
        and:
            InputStream resourceAsStream1 = new FileInputStream(SOURCE_FILE_UPLOAD_TXT1)
            MockMultipartFile upload1 = new MockMultipartFile("files", "uploadedFile1.txt", TXT_CONTENT_TYPE, resourceAsStream1)
        and:
            int fileSize = [upload0, upload1].size()
            String fileContentType = "text"
        and:
            String response = mvc.perform(MockMvcRequestBuilders.fileUpload(FileUploadController.ROOT_MAPPING)
                    .file(upload0)
                    .file(upload1)
                    .header("Content-Type", "multipart/form-data"))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn().getResponse().getContentAsString()
        and:
            List<FileUpload> fileUploads = objectMapper.readValue(response, new TypeReference<List<FileUpload>>() {})
            assert fileUploads.size() == fileSize
        when:
            mvc.perform(delete(FileUploadController.ROOT_MAPPING + "/" + fileContentType)
                    .contentType(WordParserMediaType.JSON)
                    .accept(WordParserMediaType.JSON))
                    .andExpect(status().is2xxSuccessful())
        then:
            noExceptionThrown()
            assert fileUploadComponent.getByContentType(FileContentType.TYPE_TXT).size() == 0

    }

}
