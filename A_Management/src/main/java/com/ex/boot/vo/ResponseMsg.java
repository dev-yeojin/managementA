package com.ex.boot.vo;

import java.util.List;

public class ResponseMsg {

	private String msg;
	private Member member;
	private List<Member> memberList;
	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public List<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}

	public ResponseMsg(){
		super();
	}
	
	public ResponseMsg(String msg, Member member, List<Member> memberList){
		this.msg = msg;
		this.member = member;
		this.memberList = memberList;
	}
	
}
