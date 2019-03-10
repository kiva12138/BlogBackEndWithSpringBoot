package com.SunBlog.BlogSiteBackEnd.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userinfo")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long uid;
	private String username;
	private String password;
	private String phone;
	private String birth;
	private int blognum;
	private int quesnum;
	private int resnum;
	private String regtime;
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public int getBlognum() {
		return blognum;
	}
	public void setBlognum(int blognum) {
		this.blognum = blognum;
	}
	public int getQuesnum() {
		return quesnum;
	}
	public void setQuesnum(int quesnum) {
		this.quesnum = quesnum;
	}
	public int getResnum() {
		return resnum;
	}
	public void setResnum(int resnum) {
		this.resnum = resnum;
	}
	public String getRegtime() {
		return regtime;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	
}
