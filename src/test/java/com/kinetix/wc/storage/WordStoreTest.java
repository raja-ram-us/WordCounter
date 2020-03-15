package com.kinetix.wc.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class WordStoreTest {

    @InjectMocks
    public WordStore wordStore;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addAndWordCountTest() {
        // Adding a new word and adding the same word tested here
        wordStore.add("flower");
        wordStore.add("phone");
        wordStore.add("flower");
        wordStore.add("flower");
        wordStore.add("phone");

        Map<String, Long> words = wordStore.getWordList();
        assertEquals(2, words.size());

        // Getting the count of existing and non-existing words are tested here
        assertEquals(3L, wordStore.getCount("flower").longValue());
        assertEquals(2L, wordStore.getCount("phone").longValue());
        assertEquals(0L, wordStore.getCount("test").longValue());
    }

}
