package com.SunBlog.BlogSiteBackEnd.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bloginfo")
public class BlogInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long blogid;
	private long authorid;
	private String time;
	private int readnum;
	private int commentsnum;
	private String title;
	public BlogInfo() {
		this.blogid = 0;
		this.authorid = 0;
		this.time = null;
		this.readnum = 0;
		this.title = null;
		this.commentsnum = 0;
	}
	public long getBlogid() {
		return blogid;
	}
	public void setBlogid(long blogid) {
		this.blogid = blogid;
	}
	public long getAuthorid() {
		return authorid;
	}
	public void setAuthorid(long authorid) {
		this.authorid = authorid;
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
	public int getCommentsnum() {
		return commentsnum;
	}
	public void setCommentsnum(int commentsnum) {
		this.commentsnum = commentsnum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
