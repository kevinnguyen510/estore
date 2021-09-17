package com.estore.controller;

import java.util.List;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estore.dao.ProductDAO;
import com.estore.entity.Product;
import com.estore.service.CookieService;

@Controller
public class ProductController {
	
	@Autowired
	ProductDAO pdao;
	
	@Autowired
	CookieService cookie;
	
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
		//list sản phẩm cùng loại
		List<Product> list = pdao.finByCategoryId(prod.getCategory().getId());
		model.addAttribute("prod",prod);
		model.addAttribute("list",list);
		return "product/detail";
	}
	
	@ResponseBody
	@RequestMapping("/product/add-to-favo/{id}")
	public boolean addToFavorite(Model model , @PathVariable("id") Integer id) {
		//lấy cookie tu clien gui len
		Cookie favo = cookie.read("favo");
		String value = id.toString();
		//nếu có cookie thì lấy ra
		if(favo !=null) {			
			value = favo.getValue();
			//kiểm tra id đó có chứa trong cookie chua, nếu chưa thì add vào mảng cookie, còn có rồi thì thôi ko cần add nữa
			if(!value.contains(id.toString())) {
				value += "," + id.toString();
			}else {
				return false;
			}
		}
		cookie.create("favo", value, 30);
		return true;
	}

}
