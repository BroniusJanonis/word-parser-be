package com.wordparser.demo.component

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.wordparser.demo.Spec
import com.wordparser.demo.api.DocumentDownloadDTO
import com.wordparser.demo.service.ParseFileUploadService
import org.junit.Test
import spock.lang.Subject

class DocumentDownloadComponentSpec extends Spec{

    @Subject
    DocumentDownloadComponentImpl service

    @Collaborator
    private FileUploadComponent fileUploadComponent = Mock()
    @Collaborator
    private ParseFileUploadService parseFileUploadService = Mock()

    def setup() {
        service = new DocumentDownloadComponentImpl(fileUploadComponent, parseFileUploadService)
    }

    @Test(timeout = 1000L)
    def "givenFileUploadList_whenProcessing_thenShouldGenerateDocumentDownload"(){
        given:
            List<FileUpload> fileUploads = [new FileUpload(), new FileUpload()]
        when:
            DocumentDownloadDTO result = service.getParsed()
        then:
            1 * fileUploadComponent.getByContentType(FileContentType.TYPE_TXT) >> fileUploads
            2 * parseFileUploadService.parseFileUpload(_,_)
        and:
            result != null
            result.asType(DocumentDownloadDTO)
    }

    @Test(timeout = 1000L)
    def "givenNofileUploadList_whenProcessing_thenNoParsingIsDone"(){
        when:
            DocumentDownloadDTO result = service.getParsed()
        then:
            1 * fileUploadComponent.getByContentType(FileContentType.TYPE_TXT) >> []
            0 * parseFileUploadService.parseFileUpload(_,_)
        and:
            result != null
            result.asType(DocumentDownloadDTO)
            result.getWordStartsWithAG().isEmpty()
    }

}
