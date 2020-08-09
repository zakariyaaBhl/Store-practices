package com.bhl.store.dao;

import java.util.List;

import com.bhl.store.entities.User;

public interface UserDao {

    public User findByUserName(String userName);
    public void save(User user);
    public void update(User user);
    public void delete(Long id);
    public Long maxIdinTable();
    public List<User> getAllUsers();
	public User getUserById(Long id);
	public List<User> getAllUsersByMc(String mc);
    
}
