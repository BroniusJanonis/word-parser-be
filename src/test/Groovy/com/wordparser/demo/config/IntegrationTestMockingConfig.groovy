package com.wordparser.demo.config

import com.wordparser.demo.component.DocumentDownloadComponent
import com.wordparser.demo.component.FileUploadComponent
import com.wordparser.demo.service.ParseFileUploadService
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import spock.mock.DetachedMockFactory

/**
 * An integration test illustrating how to wire everything w/ Spring,
 * but replace certain components with Spock mocks
 */
@TestConfiguration
class IntegrationTestMockingConfig {
    private DetachedMockFactory factory = new DetachedMockFactory()

    @Bean
    FileUploadComponent fileUploadComponent() {
        factory.Mock(FileUploadComponent)
    }

    @Bean
    DocumentDownloadComponent documentDownloadComponent() {
        factory.Mock(DocumentDownloadComponent)
    }

    @Bean
    ParseFileUploadService fileUploadService() {
        factory.Mock(ParseFileUploadService)
    }
}
