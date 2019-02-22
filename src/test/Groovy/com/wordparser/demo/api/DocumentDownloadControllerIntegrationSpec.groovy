package com.wordparser.demo.api

import com.wordparser.demo.component.FileContentType
import com.wordparser.demo.SpecIntegration
import com.wordparser.demo.api.configuration.WordParserMediaType
import com.wordparser.demo.component.DocumentDownloadComponent
import com.wordparser.demo.component.FileUpload
import com.wordparser.demo.component.FileUploadComponent
import com.wordparser.demo.component.FileUploadRepository
import com.wordparser.demo.fixturehelpers.FileUploadFixtureHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class DocumentDownloadControllerIntegrationSpec extends SpecIntegration {

    @Autowired
    MockMvc mvc
    @Autowired
    private FileUploadComponent fileUploadComponent
    @Autowired
    private FileUploadRepository fileUploadRepository
    @Autowired
    private DocumentDownloadComponent documentDownloadComponent

    def "given not FileUpload list when requesting then empty map returned"() {
        when:
            String response = mvc.perform(get(DocumentDownloadController.ROOT_MAPPING)
                    .accept(WordParserMediaType.JSON))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString()
        then:
            DocumentDownloadDTO documentDownloadDTO = objectMapper.readValue(response, DocumentDownloadDTO)
            assert documentDownloadDTO.getWordStartsWithAG().isEmpty()
            assert documentDownloadDTO.getWordStartsWithHN().isEmpty()
            assert documentDownloadDTO.getWordStartsWithOU().isEmpty()
            assert documentDownloadDTO.getWordStartsWithVZ().isEmpty()
    }

    def "given FileUpload list when requesting then parsed DocumentDownloadDTO returned"() {
        given:
            InputStream resourceAsStream0 = new FileInputStream(SOURCE_FILE_UPLOAD_TXT0)
            MockMultipartFile upload0 = new MockMultipartFile("files", "uploadedFile0.txt", TXT_CONTENT_TYPE, resourceAsStream0)
            InputStream resourceAsStream1 = new FileInputStream(SOURCE_FILE_UPLOAD_TXT1)
            MockMultipartFile upload1 = new MockMultipartFile("files", "uploadedFile1.txt", TXT_CONTENT_TYPE, resourceAsStream1)
            int fileSize = [upload0, upload1].size()
        when: "post FileUpload list"
            mvc.perform(MockMvcRequestBuilders.fileUpload(FileUploadController.ROOT_MAPPING)
                    .file(upload0)
                    .file(upload1)
                    .header("Content-Type", "multipart/form-data"))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn().getResponse().getContentAsString()
        then: "check if FileUpload list is saved"
            assert fileUploadComponent.getByContentType(FileContentType.TYPE_TXT).size() == fileSize
        when: "get parsed FileUpload list to DocumentDownloadDTO"
            String response = mvc.perform(get(DocumentDownloadController.ROOT_MAPPING)
                    .accept(WordParserMediaType.JSON))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString()
        then: "check if is parsed correctly"
            DocumentDownloadDTO documentDownloadDTO = objectMapper.readValue(response, DocumentDownloadDTO)
            assert !documentDownloadDTO.getWordStartsWithAG().isEmpty()
            assert !documentDownloadDTO.getWordStartsWithHN().isEmpty()
            assert documentDownloadDTO.getWordStartsWithHN().get("Metasite") == 4
            assert documentDownloadDTO.getWordStartsWithHN().get("metasite") == 8
            assert !documentDownloadDTO.getWordStartsWithOU().isEmpty()
            assert !documentDownloadDTO.getWordStartsWithVZ().isEmpty()
    }

    def "given saved FileUpload and caching is initiated successfully"() {
        given:
            InputStream resourceAsStream0 = new FileInputStream(SOURCE_FILE_UPLOAD_TXT0)
            MockMultipartFile upload0 = new MockMultipartFile("files", "uploadedFile0.txt", TXT_CONTENT_TYPE, resourceAsStream0)
            int initFileSize = [upload0].size()
        and: "saving FileUpload"
            mvc.perform(MockMvcRequestBuilders.fileUpload(FileUploadController.ROOT_MAPPING)
                    .file(upload0)
                    .header("Content-Type", "multipart/form-data"))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn().getResponse().getContentAsString()
        and: "getting DocumentDownloadDTO and initiate cashing"
            String response0 = mvc.perform(get(DocumentDownloadController.ROOT_MAPPING)
                    .accept(WordParserMediaType.JSON))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString()
        and: "checking if DocumentDownloadDTO parsed with values"
            DocumentDownloadDTO documentDownloadDTO = objectMapper.readValue(response0, DocumentDownloadDTO)
            assert documentDownloadDTO.getWordStartsWithHN().get("Metasite") == 2
        when: "avoiding cashing and saving additional FileUpload"
            InputStream resourceAsStream2 = new FileInputStream(SOURCE_FILE_UPLOAD_TXT1)
            byte[] resourceAsByteArray = inputStreamToByteArray(resourceAsStream2)
        FileUpload fileUpload = FileUploadFixtureHelper.create(resourceAsByteArray)
            fileUploadRepository.save(Arrays.asList(fileUpload))
        then: "FileUpload list is increased but DocumentDownloadDTO cash is not updated"
            assert fileUploadRepository.findAll().size() > initFileSize
            String response1 = mvc.perform(get(DocumentDownloadController.ROOT_MAPPING)
                    .accept(WordParserMediaType.JSON))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString()
            DocumentDownloadDTO documentDownloadDTO1 = objectMapper.readValue(response1, DocumentDownloadDTO)
            assert documentDownloadDTO1.getWordStartsWithHN().get("Metasite") == 2
    }

    byte[] inputStreamToByteArray(InputStream is) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream()
        int nRead
        byte[] data = new byte[1024]
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead)
        }
        return buffer.toByteArray()
    }

}
