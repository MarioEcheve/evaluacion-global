package com.globallogic.ejercicio.service;

import java.util.List;
import java.util.UUID;

import com.globallogic.ejercicio.entity.User;

public interface IUserService {

	public User save(User user);
	public User findByEmail(String email);
	public List<User> getAllUsers();
	public User updateTokenUser(UUID id, String token);
}
