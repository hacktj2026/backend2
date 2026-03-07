package com.example.hacktj.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.hacktj.model.Word;

public interface WordRepository extends MongoRepository<Word, String>
{
	Word findWordByName(String word);
	List<Word> findByLevel(String level);
	
	public long count();
}