package es.caser.spring.springbootdemo.repository;

import java.util.List;

import es.caser.spring.springbootdemo.model.User;

public interface IUserRepository {
	User save (User user);

	User findByUsername(String username);

	List<User> findAll();
}
