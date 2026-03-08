package com.example.hacktj.service;

import com.example.hacktj.model.Word;

public class Testing {
    public static void main (String[] args) {
        WordService wordService = new WordService();
        wordService.setUp();
        
            for(Word word : WordService.vocabData[1]) {
                System.out.println(word.getWord() + " " + word.getMeaning() + " " + word.getSkillLevel() + " " + word.getWordType());
            }
        }
    }
