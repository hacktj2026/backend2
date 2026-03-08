package com.example.hacktj.model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Conjugation {
    public static class presentIrg {
        public String word;
        public String[] conjugations;
        public presentIrg(String word, String[] conjugations) {
            this.word = word;
            this.conjugations = conjugations;
        }
    }

    private static HashMap<String, presentIrg> presentIrregulars = new HashMap<>();
    public Conjugation() throws Exception {
    
        BufferedReader r = new BufferedReader(new FileReader("src/main/resources/presentoIrregulares.txt"));
        int size = Integer.parseInt(r.readLine());

        for (int i = 0; i < size; i++) {
            String word = r.readLine();
            String[] conjugations = new String[6];
            for (int j = 0; j < 3; j++) {
                StringTokenizer st = new StringTokenizer(r.readLine());
                conjugations[j*2] = st.nextToken();
                conjugations[j*2 + 1] = st.nextToken();
            }
            presentIrregulars.put(word, new presentIrg(word, conjugations));
        }
    }

    
    public static String conjugatePresent(Word word, String pronoun)
    {
        String str = word.getWord();
        if(presentIrregulars.containsKey(str))
        {
            presentIrg irg = presentIrregulars.get(str);
            String[] conjugations = irg.conjugations;
            switch(pronoun)
            {
                case "yo": return conjugations[0];
                case "tú": return conjugations[1];
                case "él/ella/usted": return conjugations[2];
                case "nosotros/nosotras": return conjugations[3];
                case "vosotros/vosotras": return conjugations[4];
                case "ellos/ellas/ustedes": return conjugations[5];
            }
        }
        return conjugatePresentNoIrregular(word, pronoun);
    }

    public static String conjugateFuture(Word word, String pronoun) {
        String wordStr = word.getWord();
        
        switch (wordStr) {
            case "tener": wordStr = "tendr"; break;
            case "salir": wordStr = "saldr"; break;
            case "venir": wordStr = "vendr"; break;
            case "poner": wordStr = "pondr"; break;
            case "decir": wordStr = "dir"; break;
            case "hacer": wordStr = "har"; break;
            case "haber": wordStr = "habr"; break;
            case "poder": wordStr = "podr"; break;
            case "querer": wordStr = "querr"; break;
            case "saber": wordStr = "sabr"; break;
            case "caber": wordStr = "cabr"; break;
            case "valer": wordStr = "valdr"; break;
            default: wordStr = wordStr.substring(0, wordStr.length() - 2); break;
        }

        String ending = "";
        switch (pronoun) {
            case "yo": ending = "é"; break;
            case "tú": ending = "ás"; break;
            case "él/ella/usted": ending = "á"; break;
            case "nosotros/nosotras": ending = "emos"; break;
            case "vosotros/vosotras": ending = "éis"; break;
            case "ellos/ellas/ustedes": ending = "án"; break;
        }

        wordStr += ending;
        return wordStr;
    }

    public static String conjugateConditional(Word word, String pronoun) {
        String wordStr = word.getWord();
        
        switch (wordStr) {
            case "tener": wordStr = "tendr"; break;
            case "salir": wordStr = "saldr"; break;
            case "venir": wordStr = "vendr"; break;
            case "poner": wordStr = "pondr"; break;
            case "decir": wordStr = "dir"; break;
            case "hacer": wordStr = "har"; break;
            case "haber": wordStr = "habr"; break;
            case "poder": wordStr = "podr"; break;
            case "querer": wordStr = "querr"; break;
            case "saber": wordStr = "sabr"; break;
            case "caber": wordStr = "cabr"; break;
            case "valer": wordStr = "valdr"; break;
            default: wordStr = wordStr.substring(0, wordStr.length() - 2); break;
        }

        String ending = "";
        switch (pronoun) {
            case "yo": ending = "ía"; break;
            case "tú": ending = "ías"; break;
            case "él/ella/usted": ending = "ía"; break;
            case "nosotros/nosotras": ending = "íamos"; break;
            case "vosotros/vosotras": ending = "íais"; break;
            case "ellos/ellas/ustedes": ending = "ían"; break;
        }

        wordStr += ending;
        return wordStr;
    }

    public static String conjugatePresentNoIrregular(Word word, String pronoun) {
        String wordStr = word.getWord();
        String ending = "";

        if (wordStr.endsWith("ar")) {
            switch (pronoun) {
                case "yo": ending = "o"; break;
                case "tú": ending = "as"; break;
                case "él/ella/usted": ending = "a"; break;
                case "nosotros/nosotras": ending = "amos"; break;
                case "vosotros/vosotras": ending = "áis"; break;
                case "ellos/ellas/ustedes": ending = "an"; break;
            }
        } else if (wordStr.endsWith("er")) {
            switch (pronoun) {
                case "yo": ending = "o"; break;
                case "tú": ending = "es"; break;
                case "él/ella/usted": ending = "e"; break;
                case "nosotros/nosotras": ending = "emos"; break;
                case "vosotros/vosotras": ending = "éis"; break;
                case "ellos/ellas/ustedes": ending = "en"; break;
            }
        } else if (wordStr.endsWith("ir")) {
            switch (pronoun) {
                case "yo": ending = "o"; break;
                case "tú": ending = "es"; break;
                case "él/ella/usted": ending = "e"; break;
                case "nosotros/nosotras": ending = "imos"; break;
                case "vosotros/vosotras": ending = "ís"; break;
                case "ellos/ellas/ustedes": ending = "en"; break;
            }
        }

        wordStr = wordStr.substring(0, wordStr.length() - 2) + ending;
        return wordStr;
    }
}
