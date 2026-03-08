package com.example.hacktj.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.hacktj.model.User;

public interface UserRepository extends MongoRepository<User, String>
{
	User findByName(String name);
	
	public long count();
}
