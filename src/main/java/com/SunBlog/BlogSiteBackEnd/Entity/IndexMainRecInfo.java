package com.SunBlog.BlogSiteBackEnd.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="indexmainrecinfo")
public class IndexMainRecInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long recid;
	private long blogid;
	private String title;
	private String headline;
	private String time;
	private int readnum;
	public long getRecid() {
		return recid;
	}
	public void setRecid(long recid) {
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
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getReadnum() {
		return readnum;
	}
	public void setReadnum(int readnum) {
		this.readnum = readnum;
	}
	
}
