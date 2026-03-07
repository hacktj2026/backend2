package com.example.hacktj.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.hacktj.model.Word;

public interface WordRepository extends MongoRepository<Word, String>
{
	@Query("{name:'?0'}")
	Word findWordByName(String word);
	
	
	public long count();
}