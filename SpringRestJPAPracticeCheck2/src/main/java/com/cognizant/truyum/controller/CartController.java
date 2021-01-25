package com.cognizant.truyum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.truyum.dao.CartDTO;
import com.cognizant.truyum.exception.CartEmptyException;
import com.cognizant.truyum.exception.UserIdMismatchException;
import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.model.User;
import com.cognizant.truyum.security.AppUserDetailsService;
import com.cognizant.truyum.service.MenuItemService;
import com.cognizant.truyum.service.UserService;

@RestController
@RequestMapping("/carts")
public class CartController {
	
	@Autowired
	AppUserDetailsService appUserDetailsService;
	@Autowired
	UserService userService;
	
	@Autowired
	MenuItemService menuItemService;	
	
	@PostMapping("/addcartitem/{userId}/{menuItemId}")		
	public String addCartItem(Authentication authentication, @PathVariable("userId") int userId, 
			@PathVariable("menuItemId") int menuItemId) {
		String user = authentication.getName();
		UserDetails userDetails = appUserDetailsService.loadUserByUsername(user);
		String role = userDetails.getAuthorities().toArray()[0].toString().split("_")[1];
		User u =userService.findById(userId);
		MenuItem m=	menuItemService.searchMenuItem(menuItemId);		
		if(u.getName().equals(user) && m!=null) {
			return userService.addCartItem(u,m,role, menuItemId);
		}
		else return "User Does Not Match with the entered Id";		
	}
	
	@GetMapping("/{userId}")
	public CartDTO getAllCartItems(@PathVariable("userId") int userId){
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();		
		String user = authentication.getName();
		User u =userService.findById(userId);		
		if(u.getName().equals(user))
		{			
			if(!u.getMenuItemSet().isEmpty())
			{
				CartDTO cart=new CartDTO(u.getMenuItemSet());
				return cart;
			}
			else throw new CartEmptyException();
		}
		else throw new UserIdMismatchException();
	}
	
	
	@DeleteMapping("/removecartitem/{userId}/{menuItemId}")
	public String removeCartItem(@PathVariable("userId") int userId, @PathVariable("menuItemId") int menuItemId) {
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();		
		String user = authentication.getName();		
		User u =userService.findById(userId);
		MenuItem m=	menuItemService.searchMenuItem(menuItemId);
		if(u.getName().equalsIgnoreCase(user)) {			
			return userService.removeCartItem(u,m);
		}
		else return "User Does Not Match with the entered Id";	
	}
	
}

