package com.example.hacktj.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.hacktj.model.User;
import com.example.hacktj.model.Word;
import com.example.hacktj.model.problemBuilder;
import com.example.hacktj.repository.UserRepository;
import com.example.hacktj.repository.WordRepository;

import jakarta.annotation.PostConstruct;

@Service
public class WordService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    WordRepository wordRepository;
    @Autowired
    UserRepository userRepository;

    int max = 50;
    Word last = null;
    Scanner scan;
    public static List<Word>[] vocabData = new ArrayList[6];

    @PostConstruct
    public void setUp() {
        try {
            for(int a = 0; a < vocabData.length; a++)
                vocabData[a] = new ArrayList<>();
            Scanner scan = new Scanner(problemBuilder.class.getClassLoader().getResourceAsStream("vocabulary.txt"));
            while(scan.hasNextLine()) {
                String[] sarr = scan.nextLine().split(" ");
                vocabData[convertSkillString(sarr[2])].add(new Word(sarr[0], sarr[2], sarr[1], sarr[3], 1));
            }
            addWords(0);
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }
    public void test() {
        for(Word word : wordRepository.findByLevel(1))
            System.out.println(word.getWord());
    }
    public void addWords(int skill) {
        List<Word> existing = wordRepository.findByLevel(1);
        List<Word> words = vocabData[convertSkillString(skill)];
        Collections.shuffle(words);
        for(Word word : words) {
            if(existing.size() >= max) break;
            if(!existing.contains(word)) {
                wordRepository.save(word);
                existing.add(word);
            }
        }
    }
    public Word getNext() {
        int level = 1;
        if(Math.random() < (double)wordRepository.findByLevel(2).size() / (wordRepository.findByLevel(1).size() + wordRepository.findByLevel(2).size()))
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
            if(word.getTimesUsed() > 3)
                word.setLevel(3);
        }
        last = word;
        return word;
    }
    public void rightOrWrong(boolean answer, int level, User user) {
        user.updateSkill(last, answer);
        Query query = new Query(Criteria.where("name").is(user.getName()));
        Update update = new Update().set("skill", user.skill);
        mongoTemplate.updateFirst(query, update, User.class);
        List<Word> words = wordRepository.findByLevel(level);
        for(Word word : words) {
            if(word != null && Math.abs(convertSkillLevel(word.getSkillLevel()) - user.skill) < 20 && word.getLevel() == 1) {
                word.setLevel(2);
                wordRepository.save(word); 
            }
        }
        if(wordRepository.findByLevel(1).size() < 10) {
            addWords(user.skill);
        }   
    }

    public int convertSkillLevel(String skill) {
        if(skill.equals("A1")) return 17;
        if(skill.equals("A2")) return 34;
        if(skill.equals("B1")) return 51;
        if(skill.equals("B2")) return 68;
        if(skill.equals("C1")) return 85;
        return 101;
    }
    private int convertSkillString(int skill) {
        if(skill < 17) return 0;
        if(skill < 34) return 1;
        if(skill < 51) return 2;
        if(skill < 68) return 3;
        if(skill < 85) return 4;
        return 5;
    }
    private int convertSkillString(String skill) {
        switch(skill) {
            case "A1": return 0;
            case "A2": return 1;
            case "B1": return 2;
            case "B2": return 3;
            case "C1": return 4;
            default: return 5;
        }
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
