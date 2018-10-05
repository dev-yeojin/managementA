package com.ex.boot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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
import com.ex.boot.vo.ResponseMsg;
import com.google.gson.Gson;

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
		Gson gson = new Gson();
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		ModelAndView mv = new ModelAndView();
		HttpResponse response = null;
		
		if(member.getAuth() == 1){
				try {
				HttpGet get = new HttpGet(serverUrl + "/member/memberList");
				response = httpClient.execute(get);
				
				ResponseMsg responseMsg = memberService.jsonToResponseMsg(EntityUtils.toString(response.getEntity()));
				String resultMsg = responseMsg.getMsg();
				
				if(resultMsg.equals("SUCCESS")){
					List<Member> memberList = responseMsg.getMemberList();
					mv.addObject("memberList",gson.toJson(memberList));
					mv.setViewName("/member/memberList");
				}else{
					mv.setViewName("redirect:/error");
				}
				
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
	public ModelAndView createMember(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		HttpResponse response = null;
		Map paramMap = new HashMap<>();
		paramMap.put("userId", request.getParameter("userId"));
		paramMap.put("pwd", request.getParameter("pwd"));
		paramMap.put("name", request.getParameter("name"));
		
		try {
			HttpPost post = new HttpPost(serverUrl + "/member");
			List<NameValuePair> nvps = memberService.convertParam(paramMap);
			post.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
			response = httpClient.execute(post);
			ResponseMsg responseMsg = memberService.jsonToResponseMsg(EntityUtils.toString(response.getEntity()));
			String resultMsg = responseMsg.getMsg();
		
			if(resultMsg.equals("SUCCESS")){
				mv.addObject("msg", "가입되었습니다. 로그인해주세요.");
				mv.setViewName("redirect:/login");
			}else{
				mv.setViewName("redirect:/error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			HttpClientUtils.closeQuietly(response);
		}
		return mv;
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
	public ModelAndView editMember(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		HttpResponse response = null;
		Map paramMap = new HashMap<>();
		paramMap.put("userId", request.getParameter("userId"));
		paramMap.put("pwd", request.getParameter("pwd"));
		paramMap.put("name", request.getParameter("name"));
		
		try {
			HttpPut put = new HttpPut(serverUrl + "/member");
			List<NameValuePair> nvps = memberService.convertParam(paramMap);
			put.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
			response = httpClient.execute(put);
			ResponseMsg responseMsg = memberService.jsonToResponseMsg(EntityUtils.toString(response.getEntity()));
			String resultMsg = responseMsg.getMsg();
			
			if(resultMsg.equals("SUCCESS")){
				mv.addObject("msg", "회원 정보가 수정되었습니다. 다시 로그인해주세요.");
				mv.setViewName("redirect:/login");
			}else{
				mv.setViewName("redirect:/error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			HttpClientUtils.closeQuietly(response);
		}
		return mv;
	}
	
	//member delete
	@RequestMapping(value="/member/delete",method=RequestMethod.GET)
	public ModelAndView deleteMember(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		HttpResponse response = null;
		Map paramMap = new HashMap<>();
		paramMap.put("userId",  request.getParameter("userId"));
		
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		
		if(member.getAuth() == 1){
			try {
				HttpPost post = new HttpPost(serverUrl + "/member/delete");
				List<NameValuePair> nvps = memberService.convertParam(paramMap);
				post.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
				response = httpClient.execute(post);
				ResponseMsg responseMsg = memberService.jsonToResponseMsg(EntityUtils.toString(response.getEntity()));
				String resultMsg = responseMsg.getMsg();
				
				if(resultMsg.equals("SUCCESS")){
					mv.setViewName("redirect:/member/memberList");
				}else{
					mv.setViewName("redirect:/error");
				}
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

	@RequestMapping(value="/member/userAuth",method =RequestMethod.PUT)
	public ModelAndView updateUserAuth(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		HttpResponse response = null;
		Map paramMap = new HashMap<>();
		paramMap.put("userId", request.getParameter("userId"));
		paramMap.put("auth", Integer.parseInt(request.getParameter("auth")));
		
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		
		if(member.getAuth() == 1){
			try {
				HttpPut put = new HttpPut(serverUrl + "/member/userAuth");
				List<NameValuePair> nvps = memberService.convertParam(paramMap);
				put.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
				
				response = httpClient.execute(put);
				ResponseMsg responseMsg = memberService.jsonToResponseMsg(EntityUtils.toString(response.getEntity()));
				String resultMsg = responseMsg.getMsg();
				
				if(resultMsg.equals("SUCCESS")){
					mv.setViewName("redirect:/member/memberList");
				}else{
					mv.setViewName("redirect:/error");
				}
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
}
