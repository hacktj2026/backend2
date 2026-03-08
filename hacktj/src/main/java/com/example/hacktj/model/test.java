package com.example.hacktj.model;

public class test {
    public static void main(String[] args) {
        conjugation c = null;
        try {
            c = new conjugation();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Word word = new Word("tener");
        String pronoun = "yo";
        String conjugatedWord = conjugation.conjugatePresent(word, pronoun);
        System.out.println("Conjugated word: " + conjugatedWord);
    }
}
