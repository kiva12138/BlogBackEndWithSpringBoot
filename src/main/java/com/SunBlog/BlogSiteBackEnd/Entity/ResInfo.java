package com.SunBlog.BlogSiteBackEnd.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="resinfo")
public class ResInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long resid;
	private long authorid;
	private String time;
	private int downnum;
	private int commentsnum;
	private String format;
	private String filename;
	private String title;
	public long getResid() {
		return resid;
	}
	public void setResid(long resid) {
		this.resid = resid;
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
	public int getDownnum() {
		return downnum;
	}
	public void setDownnum(int downnum) {
		this.downnum = downnum;
	}
	public int getCommentsnum() {
		return commentsnum;
	}
	public void setCommentsnum(int commentsnum) {
		this.commentsnum = commentsnum;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
