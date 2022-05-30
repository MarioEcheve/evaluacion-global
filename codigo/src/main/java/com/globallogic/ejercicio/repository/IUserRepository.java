package com.globallogic.ejercicio.repository;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.globallogic.ejercicio.entity.User;

public interface IUserRepository extends JpaRepository<User, UUID>{
	
	public User findByEmail(String email);
	
	@Transactional
	@Query("update User usr set usr.token = :token WHERE usr.idUser = :id")
    public User updateTokenUser(@Param("id") UUID id, @Param("token") String token);
	
}
