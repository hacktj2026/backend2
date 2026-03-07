package com.example.hacktj.model;

public class User {
    public int skill;
    
    public User() {
        skill = 0;

    }

    public int getSkill() {
        return skill;
    }

    public void updateSkill(Word word, boolean isCorrect) {
        
    }

    public int skillNum(String s)
    {
        switch(s) {
            case "A1": return 0;
            case "A2": return 20;   
            case "B1": return 40;
            case "B2": return 60;
            case "C1": return 80;
            case "C2": return 100;
        }
        return 999;
    }
}
