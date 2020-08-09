package com.bhl.store.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bhl.store.entities.User;



@Repository
@Transactional("transactionManagerForSecurity")
public class UserDaoImpl implements UserDao {

	// need to inject the session factory
	@Autowired
	@Qualifier("sessionFactoryBeanForSecurity")
	private SessionFactory sessionFactory;

	@Override
	public User findByUserName(String userName) {
		// get the current hibernate session
		Session session = sessionFactory.getCurrentSession();

		// now retrieve/read from database using username
		Query<User> query = session.createQuery("from User u where u.userName like : name", User.class);
		query.setParameter("name", userName);
		User user = null;
		try {
			user = query.getSingleResult();
		} catch (Exception e) {
			/*-- if the user does not exist in DB --*/
			user = null;
		}

		return user;
	}

	@Override
	public void save(User user) {
		// get current hibernate session
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
	}
	
	@Override
	public void update(User user) {
		// get current hibernate session
		Session session = sessionFactory.getCurrentSession();

		// create the user ... finally LOL
		session.update(user);
			
	}

	@Override
	public Long maxIdinTable() {
		Session session = sessionFactory.getCurrentSession();
		Query<User> query = session.createQuery("from User u",User.class);
		List<User> users = query.getResultList();
		Long maxId = (long) users.size();
		return maxId;
	}

	@Override
	public void delete(Long id) {
		Session session=sessionFactory.getCurrentSession();
		session.delete(session.get(User.class, id));
	}
	
	@Override
	public List<User> getAllUsers() {
		Session session=sessionFactory.getCurrentSession();
		Query<User> query = session.createQuery("from User", User.class);
		List<User> users = query.getResultList();
		return users;
	}
	
	@Override
	public User getUserById(Long id) {
		Session session=sessionFactory.getCurrentSession();
		return session.get(User.class, id);
	}

	@Override
	public List<User> getAllUsersByMc(String mc) {
		Session session = sessionFactory.getCurrentSession();
		Query<User> query = session.createQuery("from User u where u.userName like : uName", User.class);
		query.setParameter("uName", mc);
		return query.getResultList();
	}

}
