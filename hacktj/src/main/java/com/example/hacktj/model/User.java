package com.example.hacktj.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Word")
public class User
{
    @Id
    private String id;

    private String name;
    private String password;
    private int skill;

    public Word(String id, String word, int timesUsed, String skillLevel, String wordType, String meaning, int level)
    {
        this.id = id;
        this.word = word;
        this.timesUsed = timesUsed;
        this.skillLevel = skillLevel;
        this.wordType = wordType;
        this.meaning = meaning;
        this.level = level;
    }
}