package com.ex.boot.service;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.ex.boot.vo.Member;

public interface MemberService {

	List<Member> jsonToMemberList(String json);
	List<NameValuePair> convertParam(Map paramMap);
}
