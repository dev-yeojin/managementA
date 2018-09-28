package com.ex.boot.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {
	//member list , 삭제 권한은 admin만 
	
	//Member select
	@RequestMapping(value="/member/memberList",method=RequestMethod.GET)
	public String getMemberList(HttpServletRequest request, HttpServletResponse response){
		return "/member/memberList";
	}
	
	//Member Create
	@RequestMapping(value="/member/create",method=RequestMethod.GET)
	public String createMemberPage(){
		return "/member/memberCreate";
	}
	
	@RequestMapping(value="/member/create",method=RequestMethod.POST)
	public void createMember(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("userId");
		String pwd = request.getParameter("pwd");
		int auth = Integer.parseInt(request.getParameter("auth"));
		
		return;
		
	}
	
	//Member Update
	@RequestMapping(value="/member/update",method=RequestMethod.GET)
	public ModelAndView editMemberPage(HttpServletRequest request, HttpServletResponse response){
		//id : 로그인 사용자
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("user");
		ModelAndView mv = new ModelAndView();
		
		if(userId == null){
			return null;
		}else {
			mv.addObject("userId", userId);
			mv.setViewName("/member/memberEdit");
			return mv;
		}
 	}
	
	@RequestMapping(value="/member/update",method=RequestMethod.POST)
	public void editMember(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("userId");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		
	}

	//Member Delete
	@RequestMapping(value="/member/delete",method=RequestMethod.POST)
	public void deleteMember(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("userId");
		
	}
}
