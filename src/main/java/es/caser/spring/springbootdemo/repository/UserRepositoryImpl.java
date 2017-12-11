package es.caser.spring.springbootdemo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import es.caser.spring.springbootdemo.exception.UserNotFoundException;
import es.caser.spring.springbootdemo.model.User;

@Repository
public class UserRepositoryImpl implements IUserRepository{
	private List<User> users=new ArrayList<>();
	
	public UserRepositoryImpl() {
		super();
		users.add(createUser("bilbo","bolson","bbolson","comarca"));
	}
	@Override
	public User save(User user) {
		users.add(user);
		return user;
	}
	@Override
	public User findByUsername(String username) {
		Optional<User> matchingObject = users.stream().
			    filter(p -> p.getUsername().equals(username)).
			    findFirst();
		return matchingObject.orElseThrow(() -> new UserNotFoundException(username));
	}
	private User createUser(String name, String surname, String username, String password) {
		return new User(name, surname, username, password);
	}
	@Override
	public  List<User> findAll() {
		
		return users;
	}
}
