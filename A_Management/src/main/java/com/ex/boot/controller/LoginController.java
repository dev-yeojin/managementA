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
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ex.boot.service.MemberService;
import com.ex.boot.vo.Member;

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
	
	@RequestMapping(value="/noAccess", method=RequestMethod.GET)
	public String noAccess(){
		return "/noAccess";
	}
	
	@RequestMapping(value="/error", method=RequestMethod.GET)
	public String error(){
		return "/error";
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginpage(){
		return "/loginPage";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest request){
		String urlPath = null;
		
		if(request.getParameter("userId") == null || request.getParameter("pwd") == null)
			return "redirect:/login";
		
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
			
			if(response.getEntity().getContentLength() == 0){
				urlPath = "redirect:/login";
			}else{
				String json = EntityUtils.toString(response.getEntity());
				Member member = (memberService.jsonToMemberList(json)).get(0);
				session.setAttribute("member", new Member(member.getUserId(), member.getPwd(), member.getName(), member.getAuth()));
				urlPath = "/loginSuccess";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			HttpClientUtils.closeQuietly(response);
		}
		return urlPath;
	}
	
	
	@RequestMapping(value="logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute("user");
		session.removeAttribute(userId);
		
		return "redirect:/login";
	}
	
	
}
