package es.caser.spring.springbootdemo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import es.caser.spring.springbootdemo.model.User;
import es.caser.spring.springbootdemo.repository.IUserRepository;



public class UserControllerTest {

	@Test
	public void should_FindUser_whenRequestingByUsername() throws Exception {
		User user = createUser("bilbo","bolson","bbolson","comarca");
		IUserRepository mockRepository = mock(IUserRepository.class);
		when(mockRepository.findByUsername("bbolson")).thenReturn(user);

		UserController controller = new UserController(mockRepository);
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

		UserController controller = new UserController(mockRepository);
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/users")
		.accept(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())    
    	.andExpect(jsonPath("$.length()", is(1)))
    	.andExpect(jsonPath("$.[0].name", is(user.getName())))
    	.andExpect(jsonPath("$.[0].surname", is(user.getSurname())))
    	.andExpect(jsonPath("$.[0].username", is(user.getUsername())));
    	

	}
	@Test
	public void should_ReturnNotFound_whenRequestingForFrodo() throws Exception {
		User user = createUser("bilbo","bolson","bbolson","comarca");
		IUserRepository mockRepository = mock(IUserRepository.class);
		when(mockRepository.findAll()).thenReturn(Arrays.asList(new User[]{user}));

		UserController controller = new UserController(mockRepository);
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/user/frodo")
		.accept(MediaType.APPLICATION_JSON))
    	.andExpect(status().isNotFound());
    	

	}
	private User createUser(String name, String surname, String username, String password) {
		return new User(name, surname, username, password);
	}
}
