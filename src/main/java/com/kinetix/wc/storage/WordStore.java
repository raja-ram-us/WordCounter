package com.kinetix.wc.storage;

import java.util.HashMap;
import java.util.Map;

public class WordStore implements IWordStore {

    private Map<String, Long> wordList;

    public WordStore() {
        wordList = new HashMap<>();
    }

    public Map<String, Long> getWordList() {
        return wordList;
    }

    // Add the new word with count as 1 else increment the word counter by 1
    public void add(String word) {
        Long count = (wordList.get(word) != null) ? (wordList.get(word) + 1) : 1L;
        wordList.put(word, count);
    }

    // Return the count of the existing word else return 0
    public Long getCount(String word) {
        return (wordList.get(word) != null) ? wordList.get(word) : 0L;
    }

}
