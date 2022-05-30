package com.globallogic.ejercicio.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.ejercicio.entity.User;
import com.globallogic.ejercicio.repository.IUserRepository;
import com.globallogic.ejercicio.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepo;

	@Override
	public User save(User user) {
		return userRepo.save(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User updateTokenUser(UUID id, String token) {
		// TODO Auto-generated method stub
		return userRepo.updateTokenUser(id, token);
	}

}
