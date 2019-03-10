package com.SunBlog.BlogSiteBackEnd.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="quesstarinfo")
public class QuesStarInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long quesstarid;
	private String name;
	private String des;
	private long userid;
	
	public long getQuesstarid() {
		return quesstarid;
	}
	public void setQuesstarid(long quesstarid) {
		this.quesstarid = quesstarid;
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
