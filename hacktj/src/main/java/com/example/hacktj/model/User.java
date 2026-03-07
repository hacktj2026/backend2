package com.example.hacktj.model;

import java.util.HashMap;

public class User {
    public HashMap<String, Integer> skills;
    
    public User() {
        skills = new HashMap<>();
    }

    public HashMap<String, Integer> getSkills() {
        return skills;
    }

    public HashMap<String, Integer> update(Word word, boolean isCorrect) {
        String skillLevel = word.getSkillLevel();
        int currentCount = skills.getOrDefault(skillLevel, 0);
    
        return skills;
    }
}
