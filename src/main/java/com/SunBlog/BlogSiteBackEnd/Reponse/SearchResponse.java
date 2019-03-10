package com.SunBlog.BlogSiteBackEnd.Reponse;

import java.util.List;

public class SearchResponse {
	private int code;
	private List<?> result;
	private int maxNum;
	private int currentPage;
	
	public SearchResponse() {
		this.code=0;
		this.maxNum = 0;
		this.currentPage = 0;
		this.result = null;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public List<?> getResult() {
		return result;
	}
	public void setResult(List<?> result) {
		this.result = result;
	}
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
}
