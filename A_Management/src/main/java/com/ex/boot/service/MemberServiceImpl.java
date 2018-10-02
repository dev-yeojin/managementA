package com.ex.boot.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.ex.boot.vo.Member;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Override
	public List<Member> jsonToMemberList(String json) {
		Gson gson = new Gson();
        List<Member> memberList = gson.fromJson(json, new TypeToken<List<Member>>(){}.getType());
        return memberList;
	}
	
	@Override
	public List<NameValuePair> convertParam(Map paramMap) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        Iterator<String> keys = paramMap.keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next();
            paramList.add(new BasicNameValuePair(key, paramMap.get(key).toString()));
        }
         
        return paramList;
	}
}
