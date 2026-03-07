package com.example.hacktj.model;

import java.util.HashMap;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class User {
    private HashMap<String, Integer> skills;
    private String name;
    private String email;
    private String password;
    public User() {
        MongoClient mongoClient = MongoClients.create(System.getenv("MONGO_URI"));
        MongoDatabase database = mongoClient.getDatabase("language-app");
        MongoCollection<Document> collection = database.getCollection("yourCollection");
        
        Document root = collection.find().first();
        Document a1 = root.get("A1", Document.class);
        
        // Iterate over keys in A1 (nouns, verbs, etc.)
        for (String key : a1.keySet()) {
            // Put each category with default score of 0
            skills.put(key, 0);
        }
        
        mongoClient.close();
        
    }

   
    


}
