package com.example.hacktj.model;

public class test {
    public static void main(String[] args) {
        Conjugation c = null;
        try {
            c = new Conjugation();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Word word = new Word("tener");
        String pronoun = "yo";
        String conjugatedWord = Conjugation.conjugatePresent(word, pronoun);
        System.out.println("Conjugated word: " + conjugatedWord);
    }
}
