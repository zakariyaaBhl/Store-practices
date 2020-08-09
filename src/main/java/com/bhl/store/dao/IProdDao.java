package com.bhl.store.dao;

import java.util.List;

import com.bhl.store.entities.Product;

public interface IProdDao {

	public List<Product> getAllProducts();
	public List<Product> getAllProductsByMc(String mc);
	public Product getProductById(Long id);
	public Product saveProd(Product product);
	public Product updateProd(Product product);
	public void deleteProd(Long id);
}
