package com.kinetix.wc.service;

import com.kinetix.external.translator.IWordTranslator;
import com.kinetix.wc.storage.IWordStore;
import com.kinetix.wc.util.AppConstants;

import java.util.regex.Pattern;

public class WordCounter implements IWordCounter {

    private IWordStore wordStore;
    private IWordTranslator translator;

    public WordCounter(IWordStore wordStore, IWordTranslator translator) {
        this.wordStore = wordStore;
        this.translator = translator;
    }

    // The given word will be validated, translated and added into the WordStore
    public Boolean addWord(String word) {
        if (isWordValid(word)) {
            String translatedWord = translator.translate(word);
            wordStore.add(translatedWord);

            System.out.println(String.format("The translated word <%s> has been added successfully", translatedWord));
            return true;
        }

        System.out.println(String.format("The <%s> word is invalid", word));
        return false;
    }

    // The given searching word will be validated, translated and searched from the WordStore
    public Long findWordCount(String word) {
        if (isWordValid(word)) {
            String translatedWord = translator.translate(word);
            return wordStore.getCount(translatedWord);
        }

        System.out.println(String.format("The <%s> word is invalid", word));
        return 0L;
    }

    // Helper method to validate a word
    private Boolean isWordValid(String word) {
        return (word != null) && Pattern.matches(AppConstants.ALPHABETIC_PATTERN, word);
    }

}
