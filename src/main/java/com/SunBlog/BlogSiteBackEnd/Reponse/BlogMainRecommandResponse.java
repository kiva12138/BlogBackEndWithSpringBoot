package com.SunBlog.BlogSiteBackEnd.Reponse;

import java.util.List;

public class BlogMainRecommandResponse {
	private int code;
	private List<?> indexMainRecInfos;
	
	public BlogMainRecommandResponse(){
		this.code = 0;
		this.indexMainRecInfos = null;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public List<?> getIndexMainRecInfos() {
		return indexMainRecInfos;
	}

	public void setIndexMainRecInfos(List<?> indexMainRecInfos) {
		this.indexMainRecInfos = indexMainRecInfos;
	}

}
