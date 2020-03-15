package com.kinetix.wc.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.kinetix.external.translator.WordTranslator;
import com.kinetix.wc.storage.WordStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WordCounterTest {

    @InjectMocks
    public WordCounter wordCounter;

    @Mock
    public WordStore wordStore;

    @Mock
    public WordTranslator wordTranslator;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    // Adding invalid words like null, number, special characters, space are tested here
    @Test
    public void addWordWithNull() {
        Boolean result = wordCounter.addWord(null);
        assertEquals(false, result);
        invalidWordTestVerifyForAddWord();
    }

    @Test
    public void addWordWithSpecialChars() {
        Boolean result = wordCounter.addWord("Test%&");
        assertEquals(false, result);
        invalidWordTestVerifyForAddWord();
    }

    @Test
    public void addWordWithNumber() {
        Boolean result = wordCounter.addWord("Test12");
        assertEquals(false, result);
        invalidWordTestVerifyForAddWord();
    }

    @Test
    public void addWordWithSpace() {
        Boolean result = wordCounter.addWord("Test Test");
        assertEquals(false, result);
        invalidWordTestVerifyForAddWord();
    }

    // Add an English word and other language word are tested here
    @Test
    public void addValidEnglishWord() {
        when(wordTranslator.translate("flower")).thenReturn("flower");
        Boolean result = wordCounter.addWord("flower");

        assertEquals(true, result);
        verify(wordTranslator, times(1)).translate("flower");
        verify(wordStore, times(1)).add("flower");
    }

    @Test
    public void addValidOtherLanguageWord() {
        when(wordTranslator.translate("flor")).thenReturn("flower");
        Boolean result = wordCounter.addWord("flor");

        assertEquals(true, result);
        verify(wordTranslator, times(1)).translate("flor");
        verify(wordStore, times(1)).add("flower");
    }

    // Finding invalid words like null, number, special characters, space are tested here

    @Test
    public void findWordCountWithNull() {
        Long result = wordCounter.findWordCount(null);
        assertEquals(0L, result.longValue());
        invalidWordTestVerifyForFindWordCount();
    }

    @Test
    public void findWordCountWithSpecialChars() {
        Long result = wordCounter.findWordCount("fl$%$^&");
        assertEquals(0L, result.longValue());
        invalidWordTestVerifyForFindWordCount();
    }

    @Test
    public void findWordCountWithNumber() {
        Long result = wordCounter.findWordCount("12flo3wer1");
        assertEquals(0L, result.longValue());
        invalidWordTestVerifyForFindWordCount();
    }

    @Test
    public void findWordCountWithSpace() {
        Long result = wordCounter.findWordCount("flower flor");
        assertEquals(0L, result.longValue());
        invalidWordTestVerifyForFindWordCount();
    }

    // Find valid English and other language words are tested here
    @Test
    public void findEnglishWordCountWithValidWord() {
        when(wordTranslator.translate("flower")).thenReturn("flower");
        when(wordStore.getCount("flower")).thenReturn(1L);

        Long result = wordCounter.findWordCount("flower");
        assertEquals(1L, result.longValue());

        verify(wordTranslator, times(1)).translate("flower");
        verify(wordStore, times(1)).getCount("flower");
    }

    @Test
    public void findOtherLanguageWordCountWithValidWord() {
        when(wordTranslator.translate("blume")).thenReturn("flower");
        when(wordStore.getCount("flower")).thenReturn(2L);

        Long result = wordCounter.findWordCount("blume");
        assertEquals(2L, result.longValue());

        verify(wordTranslator, times(1)).translate("blume");
        verify(wordStore, times(1)).getCount("flower");
    }

    private void invalidWordTestVerifyForAddWord() {
        verify(wordTranslator, times(0)).translate(any());
        verify(wordStore, times(0)).add(any());
    }

    private void invalidWordTestVerifyForFindWordCount() {
        verify(wordTranslator, times(0)).translate(any());
        verify(wordStore, times(0)).getCount(any());
    }
}
