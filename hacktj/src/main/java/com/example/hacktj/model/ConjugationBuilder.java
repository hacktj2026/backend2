package com.example.hacktj.model;

public class ConjugationBuilder extends Builder {
    Conjugation c;
    Word w;
    public ConjugationBuilder(Word wo) throws Exception {
        super();
        c = new Conjugation();
        this.w = wo;
    }

    public String problem() throws Exception {
        return "Conjugation problem";
    }

    public String problem(User u) throws Exception {
        if(u.skill < 50)
        {
            return c.conjugatePresent(w, pronoun());
        }
        else
        {
            if(Math.random() < 0.5)
                return c.conjugatePresent(w, pronoun());
            else
                return c.conjugateFuture(w, pronoun());
        }
        
    }

    public String pronoun()
    {
        int rand = (int)(Math.random() * 6);
        switch(rand)
        {
            case 0: return "yo";
            case 1: return "tú";
            case 2: return "él/ella/usted";
            case 3: return "nosotros/nosotras";
            case 4: return "vosotros/vosotras";
            case 5: return "ellos/ellas/ustedes";
        }
        return "-1";
    }
}
