package com.example.hacktj.model;

public class test {
    public static void main(String[] args) {
        ConjugationBuilder builder = null;
        Word word = new Word("tener");
        try {
            builder = new ConjugationBuilder(word);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        
        User u = new User("test", 99);
        try {
            System.out.println(builder.problem(u));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
