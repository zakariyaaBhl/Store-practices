package com.bhl.store.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



public class CrmUser {
	
	@NotNull
	@Size(min=5)
	private String userName;
	
	@NotNull
	@Size(min=2)
	private String password;
	
	@NotNull
	@Size(min=5)
	private String firstName;
	
	@NotNull
	@Size(min=5)
	private String lastName;
	
	@NotNull
	@Size(min=8)
	private String email;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CrmUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	}
