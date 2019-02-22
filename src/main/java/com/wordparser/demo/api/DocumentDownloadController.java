package com.wordparser.demo.api;

import com.wordparser.demo.api.configuration.WordParserMediaType;
import com.wordparser.demo.component.DocumentDownloadComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.wordparser.demo.configurator.MetasiteParsesServiceProperties.DEFAULT_REST_API_PREFIX;

@RestController
@RequestMapping(DocumentDownloadController.ROOT_MAPPING)
public class DocumentDownloadController {

    public static final String ROOT_MAPPING = DEFAULT_REST_API_PREFIX + "/document-download";

    private DocumentDownloadComponent documentDownloadComponent;

    @Autowired
    DocumentDownloadController(DocumentDownloadComponent documentDownloadComponent){
        this.documentDownloadComponent = documentDownloadComponent;
    }

    @CrossOrigin
    @RequestMapping(
            method = RequestMethod.GET,
            produces = WordParserMediaType.JSON)
    @ResponseStatus(HttpStatus.OK)
    public DocumentDownloadDTO getTxtParsedDocuments() throws InterruptedException {
        DocumentDownloadDTO dto = documentDownloadComponent.getParsed();
        return dto;
    }

}
