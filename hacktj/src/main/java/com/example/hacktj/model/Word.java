package com.example.hacktj.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("word")
public class Word
{
    @Id
    private String id;

    private String word;
    private int timesUsed;
    private String skillLevel;
    private String wordType;
    private String meaning;
<<<<<<< HEAD
    private int runningAccuracy;
=======
    private int level;
>>>>>>> 54786ed39784256951644f7d5ea7f8790aea2ded

    public Word(String word, int timesUsed, String skillLevel, String wordType, String meaning, int level)
    {
        this.word = word;
        this.timesUsed = timesUsed;
        this.skillLevel = skillLevel;
        this.wordType = wordType;
        this.meaning = meaning;
        this.level = level;
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
    public void setMeaning(String meaning) { this.meaning = meaning; }
    public String getMeaning() { return meaning; }
    public void setLevel(int level) { this.level = level; }
    public int getLevel() { return level; }

    public boolean equals(Word w) { return w.getWord().equals(this.word); }
}