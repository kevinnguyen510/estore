package com.estore.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estore.bean.MailInfo;
import com.estore.dao.ProductDAO;
import com.estore.entity.Product;
import com.estore.service.CookieService;
import com.estore.service.MailService;

@Controller
public class ProductController {

	@Autowired
	ProductDAO pdao;

	@Autowired
	CookieService cookie;
	
	@Autowired
	MailService mail;
	/**
	 * @param model
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("/product/list-by-category/{cid}")
	public String listByCategory(Model model, @PathVariable("cid") Integer categoryId) {
		List<Product> list = pdao.findByCategoryId(categoryId);
		model.addAttribute("list", list);
		return "product/list";
	}
	
	/**
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/product/list-by-special/{id}")
	public String listBySpecial(Model model, @PathVariable("id") Integer id) {
		List<Product> list = pdao.findBySpecial(id);
		model.addAttribute("list", list);
		return "product/list";
	}
	/**
	 * @param model
	 * @param keywords
	 * @return
	 */
	@RequestMapping("/product/find-by-keywords")
	public String listByKeywords(Model model, @RequestParam("keywords") String keywords) {
		List<Product> list = pdao.findByKeywords(keywords);
		model.addAttribute("list", list);
		return "product/list";
	}

	/**
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/product/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Product prod = pdao.findById(id);
		model.addAttribute("prod", prod);
		
		//tăng số lần xem
		prod.setViewCount(prod.getViewCount() + 1);
		pdao.update(prod);
		
		// list sản phẩm cùng loại
		List<Product> list = pdao.findByCategoryId(prod.getCategory().getId());
		model.addAttribute("list", list);

		// list sản phẩm yêu thích
		Cookie favoCookie = cookie.read("favo");
		if (favoCookie != null) {// nếu chưa có thì add vào
			String ids = favoCookie.getValue();
			List<Product> favoList = pdao.findByIds(ids);
			model.addAttribute("favo", favoList);
		}

		// list sản phẩm đã xem
		Cookie viewed = cookie.read("viewed");
		String value = id.toString();
		if (viewed != null) { // nếu chưa có thì add vào
			value = viewed.getValue();
			value += "," + id.toString();
		}
		cookie.create("viewed", value, 10);
		List<Product> viewedList = pdao.findByIds(value);
		model.addAttribute("viewed", viewedList);

		return "product/detail";
	}

	/**
	 * @param model
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/product/add-to-favo/{id}")
	public boolean addToFavorite(Model model, @PathVariable("id") Integer id) {
		// lấy cookie tu client gui len
		Cookie favo = cookie.read("favo");
		String value = id.toString();
		// nếu có cookie thì lấy ra
		if (favo != null) {
			value = favo.getValue();
			// kiểm tra id đó có chứa trong cookie chua, nếu chưa thì add vào mảng cookie,
			// còn có rồi thì thôi ko cần add nữa
			if (!value.contains(id.toString())) {
				value += "," + id.toString();
			} else {
				return false;
			}
		}
		cookie.create("favo", value, 30);
		return true;
	}
	
	/**
	 * @param model
	 * @param id
	 * @param email
	 * @param comments
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/product/send-to-friend")
	public boolean sendToFriend(Model model, MailInfo info, HttpServletRequest req) {
			/*@RequestParam("from") String from,
			@RequestParam("id") String id,
			@RequestParam("email") String email,
			@RequestParam("comments") String comments 
		) {
		//Send mail
		String subject = "Thông tin hàng hóa";
		MailInfo info = new MailInfo(from, email, subject, comments); */
		
		info.setSubject("Thông tin hàng hóa");
		try {
			String id = req.getParameter("id");
			//lấy đường dẫn hiện tại
			String link = req.getRequestURL().toString().replace("send-to-friend", "detail/"+id);
			info.setBody(info.getBody() + "<hr> <a href='" + link + "'>Xem chi tiết...</a>");
			mail.send(info);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

}
