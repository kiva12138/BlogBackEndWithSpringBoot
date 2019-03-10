package com.SunBlog.BlogSiteBackEnd.Reponse;

import com.SunBlog.BlogSiteBackEnd.Entity.QuesInfo;

public class QuesResponse {
	private int code;
	private QuesInfo quesInfo;
	private String content;
	private QuesAnswer[] quesAnswers; 

	public QuesResponse() {
		this.code = 0;
		this.quesInfo = new QuesInfo();
		this.content = "";
		this.quesAnswers = null;
	}
	
	public class QuesAnswer{
		private long userid;
		private String username;
		private String time;
		private String ansContent;
		private int seq;
		public QuesAnswer() {
			this.userid = 0;
			this.username="";
			this.time="";
			this.ansContent = "";
			this.seq = 0;
		}
		public long getUserid() {
			return userid;
		}
		public void setUserid(long userid) {
			this.userid = userid;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getAnsContent() {
			return ansContent;
		}
		public void setAnsContent(String ansContent) {
			this.ansContent = ansContent;
		}
		public int getSeq() {
			return seq;
		}
		public void setSeq(int seq) {
			this.seq = seq;
		}
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public QuesInfo getQuesInfo() {
		return quesInfo;
	}

	public void setQuesInfo(QuesInfo quesInfo) {
		this.quesInfo = quesInfo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public QuesAnswer[] getQuesAnswers() {
		return quesAnswers;
	}

	public void setQuesAnswers(QuesAnswer[] quesAnswers) {
		this.quesAnswers = quesAnswers;
	}
	
}
