package com.wordparser.demo

import com.fasterxml.jackson.databind.ObjectMapper
import com.wordparser.demo.category.IntegrationTests
import org.junit.experimental.categories.Category
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

@Category(IntegrationTests)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SpecIntegration extends Specification{

    ObjectMapper objectMapper

    String TXT_CONTENT_TYPE
    String DOCX_CONTENT_TYPE
    String SOURCE_FILE_UPLOAD_TXT0
    String SOURCE_FILE_UPLOAD_TXT1
    String SOURCE_FILE_UPLOAD_DOCX2
    String SOURCE_FILE_UPLOAD_OVER_1_MB_SIZE

    def setup(){
        objectMapper = new ObjectMapper()
        objectMapper.findAndRegisterModules()
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+2:00"))

        TXT_CONTENT_TYPE = "text/plain"
        DOCX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        SOURCE_FILE_UPLOAD_TXT0 = "src/test/resources/parser-text-0.txt"
        SOURCE_FILE_UPLOAD_TXT1 = "src/test/resources/parser-text-1.txt"
        SOURCE_FILE_UPLOAD_DOCX2 = "src/test/resources/parser-text-2.docx"
        SOURCE_FILE_UPLOAD_DOCX2 = "src/test/resources/parser-text-over-1-mb-size.txt"
    }

    def cleanup(){
    }

}
