package com.example.hacktj.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class User {
    private HashMap<String, Integer> skills;
    private String name;
    private String email;
    private String password;
    public User() {
       
        
    }

    public String build()
    {
        StringBuilder jsonContent = new StringBuilder();

        // "try-with-resources" auto-closes the file when done
        try (BufferedReader reader = new BufferedReader(new FileReader("data.json"))) {
            String str;

            // Read one line at a time until there are no more lines
            while ((str = reader.readLine()) != null) {
                jsonContent.append(str); // glue each line together
            }
        }
        catch (IOException e) 
        {
            System.out.println(e.getMessage());
        }

        return(jsonContent.toString());
    }
    


}
