package es.caser.spring.springbootdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.caser.spring.springbootdemo.model.User;
import es.caser.spring.springbootdemo.repository.IUserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	private IUserRepository userRepository;
	@Autowired
	public UserController(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	
	@GetMapping(value="/{username}")
	public User getUserProfile(@PathVariable String username) {
		return userRepository.findByUsername(username);
	}
	@GetMapping
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
}
