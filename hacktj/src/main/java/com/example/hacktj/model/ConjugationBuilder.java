package com.example.hacktj.model;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
        String str;
        String type;
        String prn = pronoun();
        if(u.skill < 50)
        {
            str = c.conjugatePresent(w, prn);
            type = "Present";
        }
        else
        {
            if(Math.random() < 0.5)
            {
                str = c.conjugateConditional(w, prn);
                type = "Conditional";
            }
            else
            {
                str = c.conjugateFuture(w, prn);
                type = "Future";
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode problem = mapper.createObjectNode();

        problem.put("question", "Conjugate the verb '" + w.getWord() + "' in the " + type + prn + " form.");
        problem.put("correctAnswer", str);
        problem.putArray("choices");
        problem.put("conjugationType", type);
        problem.put("Pronoun", prn);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(problem);
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
