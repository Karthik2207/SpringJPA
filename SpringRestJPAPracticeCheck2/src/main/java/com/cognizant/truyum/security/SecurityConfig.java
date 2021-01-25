package com.cognizant.truyum.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
	@Autowired
	AppUserDetailsService appUserDetailsService;
	
	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
	LOGGER.info("InMemoryUserDetails Method Start");
	List<UserDetails> userDetailsList = new ArrayList<>();
	userDetailsList.add(User.withUsername("user").password(passwordEncoder().encode("pwd")).roles("USER").build());
	userDetailsList.add(User.withUsername("admin").password(passwordEncoder().encode("pwd")).roles("ADMIN").build());
	LOGGER.info("InMemoryUserDetails Method End");
	return new InMemoryUserDetailsManager(userDetailsList);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		LOGGER.info("Start of configure(AuthenticationManagerBuilder auth) ");
		//auth.userDetailsService(inMemoryUserDetailsManager());
		auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder());
		LOGGER.info("End of configure(AuthenticationManagerBuilder auth) ");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		LOGGER.info("Start and End of PasswordEncoder");
		return new BCryptPasswordEncoder();		
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		LOGGER.info("Start of Configure method in Security Config class");
		httpSecurity.cors();
		httpSecurity.csrf().disable().httpBasic().and().authorizeRequests()	
		.antMatchers("/menu-items/getmenuscustomer").permitAll()
		.antMatchers("/users/signup").permitAll()
		.antMatchers("/authenticate").hasAnyRole("USER", "ADMIN")
		.anyRequest().authenticated().and().addFilter(new JwtAuthorizationFilter(authenticationManager()));
		LOGGER.info("End of Configure method in Security Config class");
	}
}
