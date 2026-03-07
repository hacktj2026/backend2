package com.example.hacktj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.hacktj.model.Word;
import com.example.hacktj.repository.WordRepository;

@Service
public class WordService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    WordRepository wordRepository;
    
    Word last = null;

    public Word getNext(int level) {
    Query countQuery = new Query(Criteria.where("level").is(level));
    long count = mongoTemplate.count(countQuery, Word.class);
    int randomIndex = (int)(Math.random() * count);
    Query query = new Query(Criteria.where("level").is(level));
    query.skip(randomIndex);
    query.limit(1);
    Word word = mongoTemplate.findOne(query, Word.class);
    if (last != null && last.equals(word) && count > 1) {
        randomIndex = (int)(Math.random() * count);
        query = new Query(Criteria.where("level").is(level));
        query.skip(randomIndex);
        query.limit(1);
        word = mongoTemplate.findOne(query, Word.class);
    }
    last = word;
    return word;
    }
}
