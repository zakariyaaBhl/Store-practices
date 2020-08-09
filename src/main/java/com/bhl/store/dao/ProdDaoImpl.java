package com.bhl.store.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bhl.store.entities.Product;

@Repository
@Transactional("transactionManagerForEntities")
public class ProdDaoImpl implements IProdDao{

	@Autowired
	@Qualifier("sessionFactoryBeanForEntities")
	private SessionFactory factory;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public List<Product> getAllProducts() {
		Session session = factory.getCurrentSession();
		Query<Product> query = session.createQuery("from Product",Product.class);
		logger.info("\n=====>>> Operation getAllProducts is done ");
		return query.getResultList();
	}

	public List<Product> getAllProductsByMc(String mc) {
		Session session = factory.getCurrentSession();
		Query<Product> query = session.createQuery("from Product p where p.designation like : x",Product.class);
		query.setParameter("x", mc);
		logger.info("\n=====>>> Operation getAllProductsByMc is done ");
		return query.getResultList();
	}

	public Product getProductById(Long id) {
		Session session = factory.getCurrentSession();
		Product p = session.get(Product.class, id);
		logger.info("\n=====>>> Operation getProductById is done ");
		return p;
	}

	public Product saveProd(Product product) {
		Session session = factory.getCurrentSession();
		session.save(product);
		logger.info("\n=====>>> Operation saveProd is done ");
		return product;
	}

	public Product updateProd(Product product) {
		Session session = factory.getCurrentSession();
		session.update(product);
		logger.info("\n=====>>> Operation updateProd is done ");
		return product;
	}

	public void deleteProd(Long id) {
		Session session = factory.getCurrentSession();
		session.delete(session.get(Product.class, id));
		logger.info("\n=====>>> Operation deleteProd is done ");
	}

}
