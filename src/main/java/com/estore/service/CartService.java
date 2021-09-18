package com.estore.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.estore.dao.ProductDAO;
import com.estore.entity.Product;

@SessionScope //scopedTarget.cartService
@Service
public class CartService {
	@Autowired
	ProductDAO dao;
	
	Map<Integer, Product> map = new HashMap<>();

	public void add(Integer id) {
		Product product = map.get(id);
		//nếu chưa có trong giỏ hàng thì thêm vào
		if(product == null) {
			product = dao.findById(id);
			product.setQuantity(1);
			map.put(id, product);
		}else {
			//nếu có trong giỏ hàng rồi thì tăng số lượng lên
			product.setQuantity(product.getQuantity() + 1);
		}
	}	

	public void update(Integer id, int qty) {
		Product product = map.get(id);
		product.setQuantity(qty);
	}	
	
	public void remove(Integer id) {
		map.remove(id);
	}
	
	public void clear(){
		map.clear();
	}
	
	public Collection <Product>  getItems(){
		return map.values();
	}
	
	public int getCount(){
		Collection<Product> listProducts = this.getItems();
		int count = 0;
		for(Product product : listProducts){
			count += product.getQuantity();
		};
		return count;
	}
	
	public double getAmount(){
		Collection<Product> listProducts = this.getItems();
		double amount = 0;
		for(Product product : listProducts){
			amount += product.getQuantity()*product.getUnitPrice()*(1-product.getDiscount());
		};
		return amount;

	}			
	
}
