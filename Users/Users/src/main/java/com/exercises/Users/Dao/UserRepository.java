package com.exercises.Users.Dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.exercises.Users.Model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	User findByUserId(String userId); 
	
	User findByEmail(String email);
	
}