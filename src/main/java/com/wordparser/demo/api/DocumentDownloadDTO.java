package com.wordparser.demo.api;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* Synchronizing threads in case if one thread is accessing map while another is updating. Throws ConcurrentModificationException
* Source of problem: https://stackoverflow.com/questions/26943492/synchronize-access-to-map-in-java
* Source of solution: https://crunchify.com/hashmap-vs-concurrenthashmap-vs-synchronizedmap-how-a-hashmap-can-be-synchronized-in-java/
*/

public class DocumentDownloadDTO {

    private final Map<String, Integer> wordStartsWithAG = new ConcurrentHashMap<>(new HashMap<String, Integer>());
    private final Map<String, Integer> wordStartsWithHN = new ConcurrentHashMap<>(new HashMap<String, Integer>());
    private final Map<String, Integer> wordStartsWithOU = new ConcurrentHashMap<>(new HashMap<String, Integer>());
    private final Map<String, Integer> wordStartsWithVZ = new ConcurrentHashMap<>(new HashMap<String, Integer>());

    public DocumentDownloadDTO() {
    }

    public Map<String, Integer> getWordStartsWithAG() {
        return wordStartsWithAG;
    }

    public Map<String, Integer> getWordStartsWithHN() {
        return wordStartsWithHN;
    }

    public Map<String, Integer> getWordStartsWithOU() {
        return wordStartsWithOU;
    }

    public Map<String, Integer> getWordStartsWithVZ() {
        return wordStartsWithVZ;
    }

}