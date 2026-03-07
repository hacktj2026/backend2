package com.example.hacktj.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.hacktj.model.Word;

public interface WordRepository extends MongoRepository<Word, String>
{
	Word findWordByName(String word);
	
	public long count();
}