package es.caser.spring.springbootdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.caser.spring.springbootdemo.model.User;
import es.caser.spring.springbootdemo.repository.IUserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	private IUserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	@Autowired
	public UserController(IUserRepository userRepository,PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	
	@GetMapping(value="/{username}")
	public User getUserProfile(@PathVariable String username) {
		return userRepository.findByUsername(username);
	}
	@GetMapping
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	@PutMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
	public User signup(@RequestBody User user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return user;
	}
}
