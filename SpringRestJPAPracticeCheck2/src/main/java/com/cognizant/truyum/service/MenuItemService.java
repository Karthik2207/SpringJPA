package com.cognizant.truyum.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.repository.MenuItemRepository;


@Service
public class MenuItemService{
	
	@Autowired
	MenuItemRepository menuItemRepository;
		
	@Transactional
	public List<MenuItem> getMenuItemListCustomer(){
		return menuItemRepository.findByActiveAndDateOfLaunchBefore("yes", new Date());
	}
	@Transactional
	public List<MenuItem> getMenuItemListAdmin(){
		return menuItemRepository.findAll();
	}
	@Transactional
	public MenuItem searchMenuItem(int id) {
		return menuItemRepository.findById(id);
	}
	
	
	@Transactional
	public MenuItem findByName(String name) {
		return menuItemRepository.findByName(name);
	}
	
	@Transactional
	public MenuItem findByNameAndActiveAndDateOfLaunchBefore(String name){
		return menuItemRepository.findByNameAndActiveAndDateOfLaunchBefore(name, "yes", new Date());
	}
	
	@Transactional
	public MenuItem findByIdAndActiveAndDateOfLaunchBefore(int id){
		return menuItemRepository.findByIdAndActiveAndDateOfLaunchBefore(id, "yes", new Date());
	}
	
	@Transactional
	public String modifyMenuItem(MenuItem menuItem) {
		if(menuItemRepository.findById(menuItem.getId())!=null) 
		{
			menuItemRepository.save(menuItem);
			return "MenuItem Modified Successfully";
		}
		else return "No such MenuItem Present to modify";
	}
	
}
