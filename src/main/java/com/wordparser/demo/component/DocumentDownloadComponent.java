package com.wordparser.demo.component;

import com.wordparser.demo.api.DocumentDownloadDTO;

public interface DocumentDownloadComponent {

    DocumentDownloadDTO getParsed() throws InterruptedException;

}

