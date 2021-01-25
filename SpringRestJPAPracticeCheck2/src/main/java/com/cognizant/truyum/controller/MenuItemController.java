package com.cognizant.truyum.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.truyum.exception.MenuItemNotFoundException;
import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.security.AppUserDetailsService;
import com.cognizant.truyum.service.MenuItemService;

@RestController
@RequestMapping("/menu-items")
public class MenuItemController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	MenuItemService service ;
	
	@Autowired
	AppUserDetailsService appUserDetailsService;
	
	@GetMapping("/getallmenuitems")
	public List<MenuItem> getAllMenuItems(){
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();		
		String user = authentication.getName();		
		UserDetails userDetails = appUserDetailsService.loadUserByUsername(user);
		String role = userDetails.getAuthorities().toArray()[0].toString().split("_")[1];		
		if(role.equalsIgnoreCase("admin"))
		{
			LOGGER.info("Role is Admin");
			return service.getMenuItemListAdmin();
		}
		else
		{
			LOGGER.info("Role is User");
			return service.getMenuItemListCustomer();
		}
	}
	
	@GetMapping("/searchMenuItem/{name}")
	public MenuItem searchMenuItem(@PathVariable("name") String name){
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();		
		String user = authentication.getName();
		UserDetails userDetails = appUserDetailsService.loadUserByUsername(user);
		String role = userDetails.getAuthorities().toArray()[0].toString().split("_")[1];
		MenuItem m=null;
		if(role.equalsIgnoreCase("admin"))
		{
			LOGGER.info("Role is Admin");
			 m= service.findByName(name);
		}
		else
		{
			LOGGER.info("Role is User");
			m= service.findByNameAndActiveAndDateOfLaunchBefore(name); 
		}
		if(m==null)
			throw new MenuItemNotFoundException();
		else return m;
	}
	
	@PutMapping("/modifymenuitem")
	public String modifyMenuItem(@RequestBody MenuItem menuItem){
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();		
		String user = authentication.getName();		
		UserDetails userDetails = appUserDetailsService.loadUserByUsername(user);
		String role = userDetails.getAuthorities().toArray()[0].toString().split("_")[1];
		if(role.equalsIgnoreCase("admin"))			
			return service.modifyMenuItem(menuItem);
		else return "User has no rights to modify";
	}
	
}



