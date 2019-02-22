package com.wordparser.demo

import com.fasterxml.jackson.databind.ObjectMapper
import com.wordparser.demo.category.UnitTests
import spock.lang.Specification
import org.junit.experimental.categories.Category

@Category(UnitTests)
class Spec extends Specification{

    ObjectMapper objectMapper

    String SOURCE_FILE_UPLOAD_TXT0

    def setup(){
        objectMapper = new ObjectMapper()
        objectMapper.findAndRegisterModules()
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+2:00"));

        SOURCE_FILE_UPLOAD_TXT0 = "src/test/resources/parser-text-0.txt"
    }

    def cleanup(){
    }
}
