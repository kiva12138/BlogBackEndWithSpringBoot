package com.SunBlog.BlogSiteBackEnd.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="quesinfo")
public class QuesInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long quesid;
	private long authorid;
	private String type;
	private String time;
	private int ansnum;
	private String title;
	public long getQuesid() {
		return quesid;
	}
	public void setQuesid(long quesid) {
		this.quesid = quesid;
	}
	public long getAuthorid() {
		return authorid;
	}
	public void setAuthorid(long authorid) {
		this.authorid = authorid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getAnsnum() {
		return ansnum;
	}
	public void setAnsnum(int ansnum) {
		this.ansnum = ansnum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
