package com.SunBlog.BlogSiteBackEnd.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="blogstarinfo")
public class BlogStarInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long blogstarid;
	private String name;
	private String des;
	private long userid;
	public long getBlogstarid() {
		return blogstarid;
	}
	public void setBlogstarid(long blogstarid) {
		this.blogstarid = blogstarid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	
}
