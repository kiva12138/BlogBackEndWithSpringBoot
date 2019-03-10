package com.SunBlog.BlogSiteBackEnd.Reponse;

import java.util.List;

public class BlogTodayRecommandResponse {
	private int code;
	private List<?> todayRecItems;
	public BlogTodayRecommandResponse() {
		this.code = 0;
		this.todayRecItems = null;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public List<?> getTodayRecItems() {
		return todayRecItems;
	}
	public void setTodayRecItems(List<?> todayRecItems) {
		this.todayRecItems = todayRecItems;
	}
}
