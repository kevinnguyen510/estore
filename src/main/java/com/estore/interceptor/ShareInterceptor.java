package com.estore.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.estore.dao.CategoryDAO;
import com.estore.entity.Category;


//cho phép lọc/chặn các method một cách độc lập mà không ảnh hưởng tới chương trình.
@Component
public class ShareInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	CategoryDAO dao;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		List<Category> list = dao.findAll();
		//modelAndView.addObject("cates",list);
		request.setAttribute("cates",list);
	}
}
