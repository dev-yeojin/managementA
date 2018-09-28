package com.ex.boot.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginpage(){
		return "/loginPage";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		
		String userId = request.getParameter("userId");
		String pwd = request.getParameter("pwd");
		
		HttpSession session = request.getSession();
		session.setAttribute("user", userId);
		
		
		if(userId.equals("admin") && pwd.equals("admin")){
			mv.setViewName("redirect:/member/memberList");
		}else{
			mv.setViewName("/loginSuccess");
		}
		
		return mv;
	}
	
	@RequestMapping(value="logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute("user");
		session.removeAttribute(userId);
		
		return "redirect:/login";
	}
}
