package com.example.hacktj.model;

public class User {
    public int skill;
    public String name;
    public String password;
    public String email;

    public User() {
        skill = 0;
        name = "";
        password = "";
        email = "";
    }
    public User(String name) {
        skill = 0;
        this.name = name;
        password = "";
        email = "";
    }
    public User(String name, String password) {
        this.name = name;
        this.password = password;
        email = "";
        skill = 0;
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        skill = 0;
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
