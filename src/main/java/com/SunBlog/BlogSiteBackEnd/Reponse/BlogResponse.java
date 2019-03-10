package com.SunBlog.BlogSiteBackEnd.Reponse;

import com.SunBlog.BlogSiteBackEnd.Entity.BlogInfo;

public class BlogResponse {
	private int code;
	private BlogInfo blogInfo;
	private String content;
	private Comment[] comments;
	
	public BlogResponse() {
		this.blogInfo = new BlogInfo();
		this.content = null;
		this.comments = null;
	}
	public class Comment{
		private long userid;
		private String time;
		private String comentContent;
		public Comment () {
			this.userid = 0;
			this.time = "";
			this.setComentContent("");
		}
		public long getUserid() {
			return userid;
		}
		public void setUserid(long userid) {
			this.userid = userid;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getComentContent() {
			return comentContent;
		}
		public void setComentContent(String comentContent) {
			this.comentContent = comentContent;
		}
	}

	public BlogInfo getBlogInfo() {
		return blogInfo;
	}

	public void setBlogInfo(BlogInfo blogInfo) {
		this.blogInfo = blogInfo;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Comment[] getComments() {
		return comments;
	}

	public void setComments(Comment[] comments) {
		this.comments = comments;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
