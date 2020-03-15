package com.kinetix.wc.storage;

public interface IWordStore {

    void add(String word);

    Long getCount(String word);
}
