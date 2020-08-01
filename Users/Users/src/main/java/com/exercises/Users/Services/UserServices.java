package com.exercises.Users.Services;

import java.util.List;

import com.exercises.Users.Dto.UserDto;
import com.exercises.Users.Model.User;


public interface UserServices {

	List<UserDto> getAllUsers(int page, int limit);

	UserDto getUserByUserId(String id);

	UserDto createUser(UserDto userDto);

	void updateUser(User user);

	void deleteUser(Long id);

	User getUserByEmail(String email);

}