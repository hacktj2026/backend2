package com.example.hacktj.model;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ConjugationBuilder extends Builder {
    Conjugation c;
    Word w;
    String type;
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
        String prn = pronoun();
        if(u.skill < 50)
        {
            type = "present";
            str = c.conjugatePresent(w, prn);
        }
        else
        {
            if(Math.random() < 0.5)
            {
                type = "conditional";
                str = c.conjugateConditional(w, prn);
            }
            else
            {
                type = "future";
                str = c.conjugateFuture(w, prn);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode problem = mapper.createObjectNode();

        problem.put("question", "Conjugate the verb '" + w.getWord() + "' in the " + prn + " form.");
        problem.put("correctAnswer", str);
        problem.putArray("choices");
        problem.put("conjugationType", type);
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
