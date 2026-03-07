package com.example.hacktj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.example.hacktj.model.Word;
import com.example.hacktj.repository.WordRepository;
import java.util.*;
import java.io.File;

@Service
public class WordService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    WordRepository wordRepository;

    User user;
    int max = 10;
    Word last = null;
    Scanner scan;

    public void setUp(int m, User user) throws Exception {
        max = m;
        this.user = user;
        scan = new Scanner(new File("hacktj\\src\\main\\resources\\vocabulary.txt"));
        addWords();
    }
    public void addWords() {
        long size = wordRepository.count();
        for(long a = size; a < max; a++) {
            String[] sarr = scan.nextLine().split(" ");
            wordRepository.save(new Word(sarr[0], sarr[2], sarr[1], sarr[3], 1));
        }
    }
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
    public void rightOrWrong(boolean answer, int level) {
        HashMap<String, Integer> skills = user.updateSkillLevel(last, answer);
        List<Word> words = wordRepository.findByLevel(level);
        for(Word word : words)
            if(convertSkillLevel(word.getSkillLevel()) < skills.get(word.getWordType()))
                wordRepository.delete(word);
        addWords();
    }
    private int convertSkillLevel(String skill) {
        if(skill.equals("A1")) return 17;
        if(skill.equals("A2")) return 34;
        if(skill.equals("B1")) return 51;
        if(skill.equals("B2")) return 68;
        if(skill.equals("C1")) return 85;
        return 101;
    }
    public void setUser(User user) { this.user = user; }
    public void setMax(int max) { this.max = max; }
    public void recordAnswer(String wordId, boolean correct) {
        Word word = wordRepository.findById(wordId).orElse(null);
        if (word != null) {
            word.setTimesUsed(word.getTimesUsed() + 1);
            wordRepository.save(word);
        }
    }
    public class WordEntry
    {
        private String spanish;
        private String english;
        
        public WordEntry() {}
        public String getSpanish() { return spanish; }
        public String getEnglish() { return english; }
        public void setSpanish(String spanish) { this.spanish = spanish; }
        public void setEnglish(String english) { this.english = english; }
    }
}
