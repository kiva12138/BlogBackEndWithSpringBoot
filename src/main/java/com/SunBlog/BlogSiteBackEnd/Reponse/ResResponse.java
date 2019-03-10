package com.SunBlog.BlogSiteBackEnd.Reponse;

import com.SunBlog.BlogSiteBackEnd.Entity.ResInfo;

public class ResResponse {
	private int code;
	private ResInfo resInfo;
	private String description;
	private ResComment[] resComments;
	
	public ResResponse() {
		this.code = 0;
		this.resInfo = new ResInfo();
		this.description = "";
		this.resComments = null;
	}
	public class ResComment{
		private long userid;
		private String name;
		private String time;
		private String content;
		public ResComment(){
			this.content="";
			this.userid = 0;
			this.time="";
			this.name = "";
		}
		public long getUserid() {
			return userid;
		}
		public void setUserid(long userid) {
			this.userid = userid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}	
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public ResInfo getResInfo() {
		return resInfo;
	}

	public void setResInfo(ResInfo resInfo) {
		this.resInfo = resInfo;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ResComment[] getResComments() {
		return resComments;
	}

	public void setResComments(ResComment[] resComments) {
		this.resComments = resComments;
	}
	
	
}
