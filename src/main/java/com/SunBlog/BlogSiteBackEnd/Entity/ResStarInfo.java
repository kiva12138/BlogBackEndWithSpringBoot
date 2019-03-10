package com.SunBlog.BlogSiteBackEnd.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="resstarinfo")
public class ResStarInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long resstarid;
	private String name;
	private int resnum;
	private long userid;
	public long getResstarid() {
		return resstarid;
	}
	public void setResstarid(long resstarid) {
		this.resstarid = resstarid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getResnum() {
		return resnum;
	}
	public void setResnum(int resnum) {
		this.resnum = resnum;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	
}
