package com.SunBlog.BlogSiteBackEnd.Reponse;

public class PicRecommandResponse {
	private int code;
	private int num;
	private PicRecItems[] items;
	
	public PicRecommandResponse() {
		this.code = 0;
		this.num = 0;
		this.items = null;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public PicRecItems[] getItems() {
		return items;
	}

	public void setItems(PicRecItems[] items) {
		this.items = items;
	}

	public class PicRecItems{
		private long blogId;
		private String title;
		private String image;
		public PicRecItems(){
			this.blogId = 0;
			this.title = null;
			this.image = null;
		}
		public long getBlogId() {
			return blogId;
		}
		public void setBlogId(long blogId) {
			this.blogId = blogId;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
	}
	

}
