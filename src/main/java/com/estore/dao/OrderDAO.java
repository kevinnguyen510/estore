package com.estore.dao;

import java.util.List;

import com.estore.entity.Order;

public interface OrderDAO {
	Order findById(Integer id);
	
	List<Order> findAll();
	
	Order create(Order entity);
	
	void update(Order entity);
	
	Order delete(Integer id);
}
