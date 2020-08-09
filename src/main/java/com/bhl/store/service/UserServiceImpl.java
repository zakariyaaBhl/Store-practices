package com.bhl.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bhl.store.dao.RoleDao;
import com.bhl.store.dao.UserDao;
import com.bhl.store.entities.Role;
import com.bhl.store.entities.User;
import com.bhl.store.user.CrmUser;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional("transactionManagerForSecurity")
public class UserServiceImpl implements UserService {

	// need to inject user & role dao 
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	
	
	private BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();;

	@Override
	public User findByUserName(String userName) {
		// check the database if the user already exists
		return userDao.findByUserName(userName);
	}

	@Override
	public void save(CrmUser crmUser) {
		User user = new User();
		//user.setId(userDao.maxIdinTable()+1);
		// assign user details to the user object
		user.setUserName(crmUser.getUserName());
		user.setPassword(bcpe.encode(crmUser.getPassword()));
		user.setFirstName(crmUser.getFirstName());
		user.setLastName(crmUser.getLastName());
		user.setEmail(crmUser.getEmail());

		// give user default role of "employee"
		user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));

		 // save user in the database
		userDao.save(user);
	}


	/*-- pour la partie Authentification : --*/
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDao.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public User findUserById(Long id) {
		return userDao.getUserById(id);
	}
	
	@Override
	public User updateUser(User user, String role) {
		// give user default role of "employee"
		
		user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE"),roleDao.findRoleByName(role)));
		
		userDao.update(user);
		return user;
	}

	@Override
	public void deleteUser(Long id) {
		userDao.delete(id);
	}

	@Override
	public List<User> getAllUsersByMc(String mc) {
		return userDao.getAllUsersByMc(mc);
	}
}
