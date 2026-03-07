package com.example.hacktj.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Word")
public class Word
{
    @Id
    private String id;

    private String word;
    private int timesUsed;
    private String skillLevel;
    private String wordType;
    private String meaning;
    private int runningAccuracy;

    public Word(String id, String word, int timesUsed, String skillLevel, String wordType, String meaning)
    {
        this.id = id;
        this.word = word;
        this.timesUsed = timesUsed;
        this.skillLevel = skillLevel;
        this.wordType = wordType;
        this.meaning = meaning;
    }


    public void setId(String id) { this.id = id; }
    public String getId() { return id; }
    public void setWord(String word) { this.word = word; }
    public String getWord() { return word; }
    public void setTimesUsed(int timesUsed) { this.timesUsed = timesUsed; }
    public int getTimesUsed() { return timesUsed; }
    public void setSkillLevel(String skillLevel) { this.skillLevel = skillLevel; }
    public String getSkillLevel() { return skillLevel; }
    public void setWordType(String wordType) { this.wordType = wordType; }
    public String getWordType() { return wordType; }
    public boolean equals(Word w) { return w.getWord().equals(this.word); }
    public void setMeaning(String meaning) { this.meaning = meaning; }
    public String getMeaning() { return meaning; }
}