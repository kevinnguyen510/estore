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
		
		//t??ng s??? l???n xem
		prod.setViewCount(prod.getViewCount() + 1);
		pdao.update(prod);
		
		// list s???n ph???m c??ng lo???i
		List<Product> list = pdao.findByCategoryId(prod.getCategory().getId());
		model.addAttribute("list", list);

		// list s???n ph???m y??u th??ch
		Cookie favoCookie = cookie.read("favo");
		if (favoCookie != null) {// n???u ch??a c?? th?? add v??o
			String ids = favoCookie.getValue();
			List<Product> favoList = pdao.findByIds(ids);
			model.addAttribute("favo", favoList);
		}

		// list s???n ph???m ???? xem
		Cookie viewed = cookie.read("viewed");
		String value = id.toString();
		if (viewed != null) { // n???u ch??a c?? th?? add v??o
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
		// l???y cookie tu client gui len
		Cookie favo = cookie.read("favo");
		String value = id.toString();
		// n???u c?? cookie th?? l???y ra
		if (favo != null) {
			value = favo.getValue();
			// ki???m tra id ???? c?? ch???a trong cookie chua, n???u ch??a th?? add v??o m???ng cookie,
			// c??n c?? r???i th?? th??i ko c???n add n???a
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
		String subject = "Th??ng tin h??ng h??a";
		MailInfo info = new MailInfo(from, email, subject, comments); */
		
		info.setSubject("Th??ng tin h??ng h??a");
		try {
			String id = req.getParameter("id");
			//l???y ???????ng d???n hi???n t???i
			String link = req.getRequestURL().toString().replace("send-to-friend", "detail/"+id);
			info.setBody(info.getBody() + "<hr> <a href='" + link + "'>Xem chi ti???t...</a>");
			mail.send(info);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

}
