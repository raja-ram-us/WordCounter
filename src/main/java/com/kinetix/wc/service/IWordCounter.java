package com.kinetix.wc.service;

public interface IWordCounter {

    Boolean addWord(String word);

    Long findWordCount(String word);
}
