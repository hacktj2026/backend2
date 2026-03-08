package com.example.hacktj.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.hacktj.model.Word;

public interface WordRepository extends MongoRepository<Word, String>
{
	List<Word> findByLevel(int level);
	Word findByName(String name);
	
	public long count();
}
