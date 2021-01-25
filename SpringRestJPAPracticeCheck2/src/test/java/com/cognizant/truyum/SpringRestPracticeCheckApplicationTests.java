package com.cognizant.truyum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.truyum.controller.UserController;
import com.cognizant.truyum.exception.UserAlreadyExistsException;
import com.cognizant.truyum.model.User;
import com.cognizant.truyum.service.UserService;


@SpringBootTest
@AutoConfigureMockMvc
class SpringRestPracticeCheckApplicationTests {
	
	@InjectMocks
	private UserController userController =new UserController();
	
	@Mock
	UserService userService;
	
	@Test	
	public void testSignupSuccess() {		
		User user=createUser();
		when(userService.findByName(user.getName())).thenReturn(null);
		when(userService.signUp(user)).thenReturn("User Created Successfully");	
		String messg=userController.signup(user);
		String expected="User Created Successfully";
		assertEquals(expected,messg);
	}
	
	@Test	
	public void testSignupFailure() {
		User user=createUser();
		when(userService.findByName(user.getName())).thenReturn(user);
		assertThrows(UserAlreadyExistsException.class, ()->userController.signup(user));
	}
	
	public User createUser() {
		User user=new User();
		user.setId(1);
		user.setName("abc");
		user.setPassword("pwd");
		return user;
	}
	
}
