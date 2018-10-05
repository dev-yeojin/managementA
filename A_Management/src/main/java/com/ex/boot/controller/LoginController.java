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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ex.boot.service.MemberService;
import com.ex.boot.vo.Member;
import com.ex.boot.vo.ResponseMsg;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class LoginController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	@Qualifier("httpClient")
	HttpClient httpClient;
	
	private static String serverUrl = "http://localhost:8181";
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String init(HttpServletRequest request){
		return "redirect:/login";
	}
	
	@RequestMapping(value="/loginSuccess", method=RequestMethod.GET)
	public String loginSuccess(){
		return "/loginSuccess";
	}
	
	@RequestMapping(value="/noAccess", method=RequestMethod.GET)
	public String noAccess(){
		return "/noAccess";
	}
	
	@RequestMapping(value="/error", method=RequestMethod.GET)
	public String error(){
		return "/error";
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView loginpage(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		String msg = request.getParameter("msg");
		
		if(msg != null){
			mv.addObject("msg",msg);
		}
		mv.setViewName("/loginPage");
		return mv;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request){
		Gson gson =  new GsonBuilder().serializeNulls().create();
		ModelAndView mv = new ModelAndView();
		
		if(request.getParameter("userId") == null || request.getParameter("pwd") == null){
			mv.addObject("msg", "아이디, 비밀번호를 입력해주세요.");
			mv.setViewName("redirect:/login");
		}else{
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("userId", request.getParameter("userId"));
			paramMap.put("pwd", request.getParameter("pwd"));
			
			HttpResponse response = null;
			HttpSession session = request.getSession();
			
			try {
				HttpPost post = new HttpPost(serverUrl + "/login");
				List<NameValuePair> loginInfo = memberService.convertParam(paramMap);
				post.setEntity(new UrlEncodedFormEntity(loginInfo,"UTF-8"));
				response = httpClient.execute(post);
				
				ResponseMsg responseMsg = memberService.jsonToResponseMsg(EntityUtils.toString(response.getEntity()));
				String resultMsg = responseMsg.getMsg();
		
				if(resultMsg.equals("LOGIN_SUCCESS")){
					Member member = responseMsg.getMember();
					session.setAttribute("member", member);
					mv.setViewName("redirect:/loginSuccess");
				}else if(resultMsg.equals("NOT_EXIST_USERID")){
					mv.addObject("msg", "존재하지 않는 아이디입니다.");
					mv.setViewName("redirect:/login");
				}else if(resultMsg.equals("LOGIN_FAIL")){
					mv.addObject("msg", "비밀번호를 확인해주세요.");
					mv.setViewName("redirect:/login");
				}else{
					mv.setViewName("redirect:/error");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				HttpClientUtils.closeQuietly(response);
			}
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
