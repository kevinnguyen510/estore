package com.estore.dao;

import java.util.List;

import com.estore.entity.Product;

public interface ProductDAO {
	Product findById(Integer id);
	
	List<Product> findAll();
	
	Product create(Product entity);
	
	void update(Product entity);
	
	Product delete(Integer id);

	List<Product> finByCategoryId(Integer categoryId);

	List<Product> finByKeywords(String keywords);
}
