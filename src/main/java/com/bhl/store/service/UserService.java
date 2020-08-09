package com.bhl.store.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.bhl.store.entities.User;
import com.bhl.store.user.CrmUser;

public interface UserService extends UserDetailsService {

    public User findByUserName(String userName);
    public User findUserById(Long id);
    public void save(CrmUser crmUser);
    public List<User> getAllUsers();
    public List<User> getAllUsersByMc(String mc);
	public User updateUser(User user, String Role);
	public void deleteUser(Long id);
}
