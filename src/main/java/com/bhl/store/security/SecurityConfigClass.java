package com.bhl.store.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bhl.store.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfigClass extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder bcpe;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/","/showProd","/showCat","/searchProd").hasAnyRole("EMPLOYEE","MANAGER","ADMIN")
			.antMatchers("/showUsers").hasRole("ADMIN")
			.antMatchers("/addFormCat","/addFormProd","/updateFormProd","/deleteProd").hasRole("MANAGER").and()
			.formLogin().loginPage("/showLoginPage").loginProcessingUrl("/authenticatedTheUser").permitAll().and()
			.logout().permitAll().and()
			.exceptionHandling().accessDeniedPage("/accessDenied");
	}
	
	@Bean
	public BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}
	
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		provider.setPasswordEncoder(bcpe);
		return provider;
	}
	
}
