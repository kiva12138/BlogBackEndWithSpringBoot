package com.SunBlog.BlogSiteBackEnd.Reponse;

import java.util.List;

public class HotQuesResponse {
	private int code;
	private List<?> items;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public List<?> getItems() {
		return items;
	}
	public void setItems(List<?> items) {
		this.items = items;
	}
	
}
