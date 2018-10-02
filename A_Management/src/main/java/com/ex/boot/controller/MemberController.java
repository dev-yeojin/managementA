package com.ex.boot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ex.boot.service.MemberService;
import com.ex.boot.vo.Member;

@Controller
public class MemberController {
	//member list , 삭제 권한은 admin만 
	@Autowired
	private MemberService memberService;
	
	@Autowired
	@Qualifier("httpClient")
	HttpClient httpClient;
	
	private static String serverUrl = "http://localhost:8181";
	
	//memberList select
	@ResponseBody
	@RequestMapping(value="/member/memberList",method=RequestMethod.GET)
	public ModelAndView getMemberList(HttpServletRequest request){
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		ModelAndView mv = new ModelAndView();
		HttpResponse response = null;
		
		if(member.getAuth() == 1){
				try {
				HttpGet get = new HttpGet(serverUrl + "/member/memberList");
				response = httpClient.execute(get);
				String json = EntityUtils.toString(response.getEntity());
				List<Member> memberList = memberService.jsonToMemberList(json);
				mv.addObject("memberList",json);
				mv.setViewName("/member/memberList");
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				HttpClientUtils.closeQuietly(response);
			}
		}else{
			mv.setViewName("redirect:/noAccess");
		}
		return mv;
	}
	
	//Member Create
	@RequestMapping(value="/member/create",method=RequestMethod.GET)
	public String createMemberPage(){
		return "/member/memberCreate";
	}
	
	@RequestMapping(value="/member/create",method=RequestMethod.POST)
	public String createMember(HttpServletRequest request){
		HttpResponse response = null;
		Map paramMap = new HashMap<>();
		paramMap.put("userId", request.getParameter("userId"));
		paramMap.put("pwd", request.getParameter("pwd"));
		paramMap.put("name", request.getParameter("name"));
		int statusCode = 0;
		
		try {
			HttpPost post = new HttpPost(serverUrl + "/member");
			List<NameValuePair> nvps = memberService.convertParam(paramMap);
			post.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
			response = httpClient.execute(post);
			statusCode = response.getStatusLine().getStatusCode();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			HttpClientUtils.closeQuietly(response);
		}
		if(statusCode == 200){
			return "redirect:/login";
		}else{
			return "redirect:/error";
		}
		
	}
	
	//Member Update
	@RequestMapping(value="/member/update",method=RequestMethod.GET)
	public ModelAndView editMemberPage(HttpServletRequest request){
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		ModelAndView mv = new ModelAndView();
		
		if(member.getUserId() == null){
			return null;
		}else {
			mv.addObject("userId", member.getUserId());
			mv.setViewName("/member/memberEdit");
			return mv;
		}
 	}
		
	@RequestMapping(value="/member/update",method=RequestMethod.POST)
	public String editMember(HttpServletRequest request){
		HttpResponse response = null;
		Map paramMap = new HashMap<>();
		paramMap.put("userId", request.getParameter("userId"));
		paramMap.put("pwd", request.getParameter("pwd"));
		paramMap.put("name", request.getParameter("name"));
		paramMap.put("auth",Integer.parseInt(request.getParameter("auth")));
		int statusCode = 0;
		
		try {
			HttpPut put = new HttpPut(serverUrl + "/member");
			List<NameValuePair> nvps = memberService.convertParam(paramMap);
			put.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
			response = httpClient.execute(put);
			statusCode = response.getStatusLine().getStatusCode();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			HttpClientUtils.closeQuietly(response);
		}
		if(statusCode == 200){
			return "redirect:/login";
		}else{
			return "redirect:/error";
		}
	}
	
	//member delete
	@RequestMapping(value="/member/delete",method=RequestMethod.GET)
	public String deleteMember(HttpServletRequest request){
		HttpResponse response = null;
		Map paramMap = new HashMap<>();
		paramMap.put("userId",  request.getParameter("userId"));
		
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		int statusCode = 0;
		
		if(member.getAuth() == 1){
			try {
				HttpPost post = new HttpPost(serverUrl + "/member/delete");
				List<NameValuePair> nvps = memberService.convertParam(paramMap);
				post.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
				response = httpClient.execute(post);
				statusCode = response.getStatusLine().getStatusCode();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				HttpClientUtils.closeQuietly(response);
			}
			if(statusCode == 200){
				return "redirect:/member/memberList";
			}else{
				return "redirect:/error";
			}
		}else{
			return "redirect:/noAccess";
		}
		
	}

}
