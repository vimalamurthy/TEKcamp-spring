package com.exercises.Users.Services.Implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.exercises.Users.Dao.UserRepository;
import com.exercises.Users.Dto.UserDto;
import com.exercises.Users.Model.User;
import com.exercises.Users.Services.UserServices;
import com.exercises.Users.Shared.Utils;

@Service
public class UserServiceImplementation implements UserServices {

	private UserRepository userRepository;
	private Utils utils; 

	public UserServiceImplementation(UserRepository userRepository, Utils utils) {
		this.userRepository = userRepository;
		this.utils = utils; 
	}

	@Override
	public List<UserDto> getAllUsers(int page, int limit) {
		
		List<User> userList = new ArrayList<User>(); 
		
		if (page>0) page --; 
		PageRequest pageableRequest = PageRequest.of(page, limit); 
		
		Page<User> userPageList = userRepository.findAll(pageableRequest);
		
		userList = userPageList.getContent(); 
		
		
		List<UserDto> userDtoList = new ArrayList<UserDto>(); 
		
		for ( int i = 0; i<userList.size(); i++ ) {
			UserDto userDto = new UserDto(); 
			BeanUtils.copyProperties(userList.get(i), userDto);
			userDtoList.add(userDto); 
		}
		
		
		return userDtoList;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		User user = userRepository.findByUserId(userId); 
		UserDto returnValue = new UserDto(); 
		BeanUtils.copyProperties(user, returnValue);
		return returnValue;
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = new User(); 
		BeanUtils.copyProperties(userDto, user);
		
		user.setEncryptedPassword("password-test"); // how to encrypt password ? 
		user.setEmailVerification(true);            // how to verify the email ?
		user.setUserId(utils.generateUserId(20)); 
		
		User createdUser = userRepository.save(user);
		
		UserDto returnUser = new UserDto(); 
		BeanUtils.copyProperties(createdUser, returnUser);
		
		return returnUser; 
	}

	@Override
	public void updateUser(User user) {
		ArrayList<User> users = (ArrayList<User>) userRepository.findAll(); 
		for (int i = 0; i<users.size(); i++ ) {
			if (users.get(i).getId() == user.getId()) {
				userRepository.save(user); 
			}
		}
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User getUserByEmail(String email) {
		User returnUser = userRepository.findByEmail(email); 
		return returnUser; 
	} 
	
}

