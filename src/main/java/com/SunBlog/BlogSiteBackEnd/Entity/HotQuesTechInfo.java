package com.SunBlog.BlogSiteBackEnd.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hotquestechinfo")
public class HotQuesTechInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int recid;
	private long quesid;
	private String title;
	private String headline;
	private int joinnum;
	private String time;
	public int getRecid() {
		return recid;
	}
	public void setRecid(int recid) {
		this.recid = recid;
	}
	public long getQuesid() {
		return quesid;
	}
	public void setQuesid(long quesid) {
		this.quesid = quesid;
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
	public int getJoinnum() {
		return joinnum;
	}
	public void setJoinnum(int joinnum) {
		this.joinnum = joinnum;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
