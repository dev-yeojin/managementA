package com.ex.boot.service;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.ex.boot.vo.Member;
import com.ex.boot.vo.ResponseMsg;
import com.google.gson.Gson;

public interface MemberService {

	
	ResponseMsg jsonToResponseMsg(String json);
	List<Member> jsonToMemberList(String json);
	List<NameValuePair> convertParam(Map paramMap);
}
