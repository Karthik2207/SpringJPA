package com.cognizant.truyum.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.truyum.model.MenuItem;


@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer>{
	
	public List<MenuItem> findByActiveAndDateOfLaunchBefore(String active, Date date);
	
	public MenuItem findById(int id);
	
	public MenuItem findByName(String name);
	
	public MenuItem findByNameAndActiveAndDateOfLaunchBefore(String name, String active, Date date);

	public MenuItem findByIdAndActiveAndDateOfLaunchBefore(int id, String active, Date date);
	
}
