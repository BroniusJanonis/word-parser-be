package com.wordparser.demo.api;

import com.wordparser.demo.api.configuration.WordParserMediaType;
import com.wordparser.demo.component.FileUpload;
import com.wordparser.demo.component.FileUploadComponent;
import com.wordparser.demo.component.FileContentType;
import com.wordparser.demo.component.validation.FileUploadValidationFactory;
import com.wordparser.demo.service.ConverterMultipartFileService;
import com.wordparser.demo.validator.ExpectChecked;
import com.wordparser.demo.validator.ValidationError;
import com.wordparser.demo.validator.ValidationException;
import com.wordparser.demo.configurator.MetasiteParsesServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(FileUploadController.ROOT_MAPPING)
public class FileUploadController {

//    TODO 4. Integracinius ir unit testais cover'int apps'ą
//    TODO 5. Jenkins butu nice pasidaryti
//    TODO 7. Autorizacija butu nice to have
//    TODO 9. Thread servisa pakurt ir sutvarkyt
//    TODO 10. Auditine lentele, dependencies nesutampa hibernate ir envers ir dar gal kazko
//    TODO 15. CRONN uždėt delete vidurnakt5 konfiguracijoj ir ant metodo (greičiausiai reikės dependenčio)
//    TODO 18. Pakeisti @CacheEvict(value = DOCUMENT_DOWNLOAD_CACHE, allEntries = true) į contentType key
//    TODO 19. Hiden Keys pasidaryt
//    TODO 22. Suvienodint dependencius visuose mikriukuose
//    TODO 24. Padaryti digital ocean open portus tik Portal, Gateway, DB. Eureka, Config servisus palikt privačiuose
//    TODO 25. Padaryti papildomus servisus per Feign, jog komunikuotų su BE servisu. Pridėt Hystrict circuit breaker

    public static final String ROOT_MAPPING = MetasiteParsesServiceProperties.DEFAULT_REST_API_PREFIX + "/file-upload";

    private FileUploadComponent fileUploadComponent;
    private FileUploadValidationFactory fileUploadValidationFactory;
    private ConverterMultipartFileService converterMultipartFileService;

    @Autowired
    FileUploadController(FileUploadComponent fileUploadComponent
                         , FileUploadValidationFactory fileUploadValidationFactory,
                         ConverterMultipartFileService converterMultipartFileService) {
        this.fileUploadComponent = fileUploadComponent;
        this.fileUploadValidationFactory = fileUploadValidationFactory;
        this.converterMultipartFileService = converterMultipartFileService;
    }

    @CrossOrigin
    @RequestMapping(
            method = RequestMethod.POST,
            produces = WordParserMediaType.JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<FileUpload> txtFileUpload(@RequestParam("files") final List<MultipartFile> files) throws IOException {
        List<ValidationError> errors = fileUploadValidationFactory.getFileUploadValidator().check(files);
        ExpectChecked.isTrue(errors.isEmpty(), ValidationException.supplier(errors));
        List<FileUpload> fileUploads = new ArrayList<>();
        fillFileUpload(files, fileUploads);
        return fileUploadComponent.saveAll(fileUploads);
    }

    private void fillFileUpload(List<MultipartFile> src, List<FileUpload> fileUploads) throws IOException {
        for (MultipartFile file : src) {
            ByteArrayOutputStream buffer = converterMultipartFileService.toByteArrayOutputStream(file);
            FileUpload fileUpload = FileUpload.FileUploadBuilder
                    .aFileUpload()
                    .withFileName(file.getOriginalFilename())
                    .withContentType(FileContentType.TYPE_TXT)
                    .withUploadedOn(LocalDateTime.now())
                    .withFileContent(buffer.toByteArray())
                    .build();
            fileUploads.add(fileUpload);
        }
    }

    @CrossOrigin
    @RequestMapping(
            value = "/{contentType}",
            method = RequestMethod.DELETE,
            produces = WordParserMediaType.JSON)
    @ResponseStatus(HttpStatus.OK)
    public Boolean removeAllByContentType(@PathVariable(value = "contentType") String contentType) {
        return fileUploadComponent.deleteAllByContentType(contentType);
    }

}