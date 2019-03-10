package com.SunBlog.BlogSiteBackEnd.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="indextodayrec")
public class BlogTodayRecInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int recid;
	private long blogid;
	private String title;
	private String picurl;
	public int getRecid() {
		return recid;
	}
	public void setRecid(int recid) {
		this.recid = recid;
	}
	public long getBlogid() {
		return blogid;
	}
	public void setBlogid(long blogid) {
		this.blogid = blogid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	
}
