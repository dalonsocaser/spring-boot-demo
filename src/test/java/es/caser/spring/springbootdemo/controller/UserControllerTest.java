package es.caser.spring.springbootdemo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.caser.spring.springbootdemo.advice.UserControllerAdvice;
import es.caser.spring.springbootdemo.exception.UserNotFoundException;
import es.caser.spring.springbootdemo.model.User;
import es.caser.spring.springbootdemo.repository.IUserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired private ObjectMapper mapper;
	@Test
	public void should_FindUser_whenRequestingByUsername() throws Exception {
		User user = createUser("bilbo","bolson","bbolson","comarca");
		IUserRepository mockRepository = mock(IUserRepository.class);
		when(mockRepository.findByUsername("bbolson")).thenReturn(user);

		UserController controller = new UserController(mockRepository,null);
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/users/bbolson")
		.accept(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())    	
    	.andExpect(jsonPath("$.name", is(user.getName())))
    	.andExpect(jsonPath("$.surname", is(user.getSurname())))
    	.andExpect(jsonPath("$.username", is(user.getUsername())));
    	

	}
	@Test
	public void should_ReturnAllUsers_whenRequestingFindAll() throws Exception {
		User user = createUser("bilbo","bolson","bbolson","comarca");
		IUserRepository mockRepository = mock(IUserRepository.class);
		when(mockRepository.findAll()).thenReturn(Arrays.asList(new User[]{user}));

		UserController controller = new UserController(mockRepository,null);
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/users")
		.accept(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())    
    	.andExpect(jsonPath("$.length()", is(1)))
    	.andExpect(jsonPath("$.[0].name", is(user.getName())))
    	.andExpect(jsonPath("$.[0].surname", is(user.getSurname())))
    	.andExpect(jsonPath("$.[0].username", is(user.getUsername())));
    	

	}
	@Test()
	public void should_ReturnNotFound_whenRequestingForFrodo() throws Exception {		
		IUserRepository mockRepository = mock(IUserRepository.class);
		when(mockRepository.findByUsername("frodo")).thenThrow(new UserNotFoundException("frodo"));

		UserController controller = new UserController(mockRepository,null);
		MockMvc mockMvc = standaloneSetup(controller)
				.setControllerAdvice(new UserControllerAdvice())
				.build();
		mockMvc.perform(get("/users/frodo")
		.accept(MediaType.APPLICATION_JSON))
    	.andExpect(status().isNotFound());
    	

	}
	@Test
	public void should_ReturnUser_WhenRequestingSignup() throws Exception {
		User user = createUser("bilbo","bolson","bbolson","comarca");
		IUserRepository mockRepository = mock(IUserRepository.class);
		when(mockRepository.save(user)).thenReturn(user);

		UserController controller = new UserController(mockRepository,passwordEncoder);
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(put("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(user))
		.accept(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$.name", is(user.getName())))
    	.andExpect(jsonPath("$.surname", is(user.getSurname())))
    	.andExpect(jsonPath("$.username", is(user.getUsername())));
    	
    	

	}
	private User createUser(String name, String surname, String username, String password) {
		return new User(name, surname, username, password);
	}
}
