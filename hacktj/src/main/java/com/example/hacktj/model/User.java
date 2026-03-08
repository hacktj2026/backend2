package com.example.hacktj.model;
import org.springframework.data.mongodb.core.index.Indexed;

public class User {
<<<<<<< HEAD
    private int skill;
    @Indexed(unique = true)
=======
    public int skill;
>>>>>>> fd9b6ecc5a67e435b1e3161132f3b4f9b2f39ffa
    private String name;

    public User() {
        skill = 0;
        name = "";
    }
    public User(String name) {
        skill = 0;
        this.name = name;
    }

    public int getSkill() {
        return skill;
    }

    public int updateSkill(Word word, boolean isCorrect) {
        int WordSkill = skillNum(word.getSkillLevel());
        boolean bigger = WordSkill > skill;
        int change = (int)Math.sqrt(WordSkill * WordSkill - skill * skill)/2;
        if(isCorrect) {
            if(bigger)
             skill += change; 
            else
             skill += Math.log(change);
        } 
        else {
            if(bigger)
             skill -= Math.log(change);
            else
              skill -= change;
        }

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
