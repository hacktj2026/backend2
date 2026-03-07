package com.example.hacktj.model;

public class conjugation {
    public conjugation() {
        super();
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

    public String conjugatePresentNoIrregular(Word word, String pronoun) {
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
