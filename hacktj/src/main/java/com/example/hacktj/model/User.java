package com.example.hacktj.model;
import org.springframework.data.mongodb.core.index.Indexed;

public class User {
    public int skill;
    @Indexed(unique = true)
    private String name;

    public User() {
        skill = 0;
        name = "";
    }
    public User(String name) {
        skill = 0;
        this.name = name;
    }
    public User(String name, int skill) {
        this.skill = skill;
        this.name = name;
    }
    
    public int getSkill() {
        return skill;
    }
    public String getName() { return name; }
    public int updateSkill(Word word, boolean isCorrect) {
        System.out.println(skill);
        int WordSkill = skillNum(word.getSkillLevel());
        boolean bigger = WordSkill > skill;
        int change = (int)(WordSkill-skill);
        if(isCorrect)
            change = Math.max(change, 5);
        if(isCorrect) {
            if(bigger)
             skill += change * 1.5; 
            else
             skill += change;
        }
        System.out.println(skill);
        return skill;
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
