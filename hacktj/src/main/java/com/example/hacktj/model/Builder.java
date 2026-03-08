package com.example.hacktj.model;

public abstract class Builder {
    public Builder() {
    }
    abstract String problem() throws Exception;
    protected static int changeDiffLevel(String s) {
        System.out.println(s);
        switch(s) {
            case "A1": return 0;
            case "A2": return 1;
            case "B1": return 2;
            case "B2": return 3;
            case "C1": return 4;
            case "C2": return 5;
            default: return -1;
        }
    }

    

}
