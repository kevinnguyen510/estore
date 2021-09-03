package com.estore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estore.dao.ProductDAO;
import com.estore.entity.Product;

@Controller
public class ProductController {
	
	@Autowired
	ProductDAO pdao;
	
	@RequestMapping("/product/list-by-category/{cid}")
	public String listByCategory(Model model , @PathVariable("cid") Integer categoryId) {
		List<Product> list = pdao.finByCategoryId(categoryId);
		model.addAttribute("list",list);
		return "product/list";
	}
	
	@RequestMapping("/product/find-by-keywords")
	public String listByKeywords(Model model , @RequestParam("keywords") String keywords) {
		List<Product> list = pdao.finByKeywords(keywords);
		model.addAttribute("list",list);
		return "product/list";
	}
	
	@RequestMapping("/product/detail/{id}")
	public String detail(Model model , @PathVariable("id") Integer id) {
		Product prod = pdao.findById(id);
		model.addAttribute("prod",prod);
		return "product/detail";
	}

}
