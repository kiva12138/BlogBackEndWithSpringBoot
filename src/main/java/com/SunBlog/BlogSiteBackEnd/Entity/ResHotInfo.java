package com.SunBlog.BlogSiteBackEnd.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reshotinfo")
public class ResHotInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int recid;
	private long resid;
	private String title;
	private int downnum;
	private String format;
	private String time;
	public int getRecid() {
		return recid;
	}
	public void setRecid(int recid) {
		this.recid = recid;
	}
	public long getResid() {
		return resid;
	}
	public void setResid(long resid) {
		this.resid = resid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getDownnum() {
		return downnum;
	}
	public void setDownnum(int downnum) {
		this.downnum = downnum;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
