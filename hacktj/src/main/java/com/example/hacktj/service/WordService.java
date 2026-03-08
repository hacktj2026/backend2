package com.example.hacktj.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.hacktj.model.User;
import com.example.hacktj.model.Word;
import com.example.hacktj.repository.WordRepository;

import jakarta.annotation.PostConstruct;

@Service
public class WordService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    WordRepository wordRepository;

    int max = 10;
    Word last = null;
    Scanner scan;

    @PostConstruct
    public void setUp() {
        try {
            scan = new Scanner(getClass().getResourceAsStream("/vocabulary.txt"));
            addWords(0);
        }
        catch(Exception e) {
            System.out.println("Error");
        }
    }
    public void addWords(int skill) {
        long size = wordRepository.count();
        long a = size;
        while(a < max) {
            String[] sarr = scan.nextLine().split(" ");
            if(convertSkillLevel(sarr[2]) >= skill) {
                wordRepository.save(new Word(sarr[0], sarr[2], sarr[1], sarr[3], 1));
                a++;
            }
        }
    }
    public Word getNext() {
        int level = 1;
        if(Math.random() < wordRepository.findByLevel(2).size() / (wordRepository.findByLevel(1).size() + wordRepository.findByLevel(2).size()))
            level = 2;
        Query countQuery = new Query(Criteria.where("level").is(level));
        long count = mongoTemplate.count(countQuery, Word.class);
        if (count == 0) {
            return null;
        }
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
        if(level == 2) {
            word.setTimesUsed(word.getTimesUsed() + 1);
            if(word.getTimesUsed() > 5)
                word.setLevel(3);
        }
        last = word;
        return word;
    }
    public void rightOrWrong(boolean answer, int level, User user) {
        int skill = user.updateSkill(last, answer);
        List<Word> words = wordRepository.findByLevel(level);
        for(Word word : words)
            if(word != null && Math.abs(convertSkillLevel(word.getSkillLevel()) - user.skill) < 10 && word.getLevel() == 1)
                word.setLevel(2);
    }
    private int convertSkillLevel(String skill) {
        if(skill.equals("A1")) return 17;
        if(skill.equals("A2")) return 34;
        if(skill.equals("B1")) return 51;
        if(skill.equals("B2")) return 68;
        if(skill.equals("C1")) return 85;
        return 101;
    }
    public void setMax(int max) { this.max = max; }
    public void recordAnswer(String wordId, boolean correct) {
        Word word = wordRepository.findById(wordId).orElse(null);
        if (word != null) {
            word.setTimesUsed(word.getTimesUsed() + 1);
            wordRepository.save(word);
        }
    }
}
