package com.ex.boot.vo;

import java.util.Date;

public class Member {

	private int idx;
	private String userId;
	private String pwd;
	private String name;
	private int auth;
	private Date createdDate;
	private Date updateDate;
	
	public Member(){}

	public Member(String userId, String pwd, String name){
		this.userId = userId;
		this.pwd = pwd;
		this.name = name;
	}
	
	public Member(String userId, String pwd, String name, int auth){
		this.userId = userId;
		this.pwd = pwd;
		this.name = name;
		this.auth = auth;
	}
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAuth() {
		return auth;
	}
	public void setAuth(int auth) {
		this.auth = auth;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
}
