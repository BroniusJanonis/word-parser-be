package com.wordparser.demo.fixturehelpers

import com.wordparser.demo.component.FileUpload

import java.time.LocalDateTime

class FileUploadFixtureHelper {
    static FileUpload create(byte[] fileContent) {
        FileUpload fileUpload = new FileUpload()
        fileUpload.setFileName("originalFileName")
        fileUpload.setFileContent(fileContent)
        fileUpload.setUploadedOn(LocalDateTime.now())
        return fileUpload
    }
}
