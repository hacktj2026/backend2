package com.example.hacktj.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("word")
public class Word
{
    @Id
    private String id;

    private String word;
    private int timesUsed = 0;
    private String skillLevel;
    private String wordType;
    private String meaning;
    private int level;

    public Word() {}
    
    public Word(String word, String skillLevel, String wordType, String meaning, int level)
    {
        this.word = word;
        this.skillLevel = skillLevel;
        this.wordType = wordType;
        this.meaning = meaning;
        this.level = level;
    }

    public Word(String word)
    {
        this.word = word;
        this.skillLevel = "";
        this.wordType = "";
        this.meaning = "";
        this.level = 3;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word w = (Word) o;
        return w.getWord().equals(this.word);
    }
}
