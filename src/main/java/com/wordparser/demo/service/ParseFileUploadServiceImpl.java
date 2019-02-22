package com.wordparser.demo.service;

import com.wordparser.demo.api.DocumentDownloadDTO;
import com.wordparser.demo.component.FileUpload;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class ParseFileUploadServiceImpl implements ParseFileUploadService {

    private static final String ENCODING = "UTF8";
    private static final String REGEX_TO_WORDS = "\\W";
    private static final String REGEX_A_TO_G = ".*[A-Ga-g].*";
    private static final String REGEX_H_TO_N = ".*[H-Nh-n].*";
    private static final String REGEX_O_TO_U = ".*[O-Uo-u].*";
    private static final String REGEX_V_TO_Z = ".*[V-Zv-z].*";

    @Override
    public void parseFileUpload(@NotNull FileUpload src, @NotNull DocumentDownloadDTO documentDownloadDTO) {
        byte[] inputStream = src.getFileContent();
        String text = getText(inputStream);
        String[] words = text.split(REGEX_TO_WORDS);
        for (String word : words) {
            mapWords(word, documentDownloadDTO.getWordStartsWithAG(), REGEX_A_TO_G);
            mapWords(word, documentDownloadDTO.getWordStartsWithHN(), REGEX_H_TO_N);
            mapWords(word, documentDownloadDTO.getWordStartsWithOU(), REGEX_O_TO_U);
            mapWords(word, documentDownloadDTO.getWordStartsWithVZ(), REGEX_V_TO_Z);
        }
    }

    private String getText(byte[] inputStream) {
        String text = null;
        try {
            text = new String(inputStream, ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }

    private void mapWords(String word, Map<String, Integer> mapper, String regex){
        if (!word.isEmpty() && word.substring(0, 1).matches(regex)) {
            int count = mapper.getOrDefault(word, 0);
            mapper.put(word, count + 1);
        }
    }
}
