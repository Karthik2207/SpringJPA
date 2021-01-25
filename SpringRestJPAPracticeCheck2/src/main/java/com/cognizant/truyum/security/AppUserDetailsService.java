package com.cognizant.truyum.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognizant.truyum.model.User;
import com.cognizant.truyum.repository.UserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("UserDetails LoadUserByUsername method called in AppUserDeatilsService Class");
		User user= userRepository.findByName(username);		
		if(user==null)
			throw new UsernameNotFoundException("User Not Found");
		return new AppUser(user);
	}
	
	

}
