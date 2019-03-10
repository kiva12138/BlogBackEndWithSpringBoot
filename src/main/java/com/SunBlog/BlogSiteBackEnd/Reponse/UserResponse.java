package com.SunBlog.BlogSiteBackEnd.Reponse;

import com.SunBlog.BlogSiteBackEnd.Entity.User;

public class UserResponse {
	private int code;
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
}
