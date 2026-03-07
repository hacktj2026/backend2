package com.example.hacktj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.example.hacktj.model.Word;
import com.example.hacktj.repository.WordRepository;
import java.util.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;

@Service
public class WordService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    WordRepository wordRepository;

    User user;
    int max = 10;
    Word last = null;

    public void setUp(int m) throws Exception {
        max = m;
        ObjectMapper mapper = new ObjectMapper();
        InputStream input = getClass().getResourceAsStream("/words.json");
        Map<String, Map<String, List<WordEntry>>> data = mapper.readValue(input,new TypeReference<Map<String, Map<String, List<WordEntry>>>>() {});
        int count = 0;
        for (String level : data.keySet()) {
            Map<String, List<WordEntry>> wordTypes = data.get(level);
            for (String wordType : wordTypes.keySet()) {
                List<WordEntry> words = wordTypes.get(wordType);
                for (WordEntry entry : words) {
                    if(count > max) break;
                    Word word = new Word(entry.getSpanish(), 0, level, wordType, entry.getEnglish(), 0);
                    wordRepository.save(word);
                    count++;
                }
            }
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
