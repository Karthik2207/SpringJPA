package com.cognizant.truyum.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.model.User;
import com.cognizant.truyum.repository.UserRepository;


@Service
public class UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MenuItemService menuItemService;

	@Transactional
	public User findByName(String name) {
		return userRepository.findByName(name); 
	}
	
	@Transactional
	public User findById(int id) {
		return userRepository.findById(id); 
	}
	
	@Transactional
	public String signUp(User user) {
		userRepository.save(user);
		return "User Created Successfully";
	}
	
	public String removeCartItem(User u,MenuItem m) {
		for(MenuItem m1:u.getMenuItemSet())
		{
			if(m1.equals(m))
			{
				u.getMenuItemSet().remove(m1);
				userRepository.save(u);
				return "Removed Successfully";
			}
		}
		return "MenuItem Not Found";
	}
	
	public String addCartItem(User u,MenuItem m,String role, int menuItemId) {
		if(role.equalsIgnoreCase("admin"))
		{
			u.getMenuItemSet().add(m);
			userRepository.save(u);
			return "MenuItem added to cart successfully";
		}
		else
		{
			 if((m=menuItemService.findByIdAndActiveAndDateOfLaunchBefore(menuItemId))!=null) {
				 u.getMenuItemSet().add(m);
				 userRepository.save(u);
				 return "MenuItem added to cart successfully";
			 }
			 else return "MenuItem with the given ID is not present"; 
		}
	}
}
