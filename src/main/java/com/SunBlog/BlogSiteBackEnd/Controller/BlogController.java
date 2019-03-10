package com.SunBlog.BlogSiteBackEnd.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SunBlog.BlogSiteBackEnd.Entity.BlogInfo;
import com.SunBlog.BlogSiteBackEnd.Entity.IndexMainRecInfo;
import com.SunBlog.BlogSiteBackEnd.Reponse.BlogMainRecommandResponse;
import com.SunBlog.BlogSiteBackEnd.Reponse.BlogResponse;
import com.SunBlog.BlogSiteBackEnd.Reponse.BlogStarResponse;
import com.SunBlog.BlogSiteBackEnd.Reponse.BlogTodayRecommandResponse;
import com.SunBlog.BlogSiteBackEnd.Reponse.PicRecommandResponse;
import com.SunBlog.BlogSiteBackEnd.Reponse.PicRecommandResponse.PicRecItems;
import com.SunBlog.BlogSiteBackEnd.Reponse.SearchResponse;
import com.SunBlog.BlogSiteBackEnd.Service.BlogService;
import com.SunBlog.BlogSiteBackEnd.Service.BlogStarService;
import com.SunBlog.BlogSiteBackEnd.Service.BlogTodayRecService;
import com.SunBlog.BlogSiteBackEnd.Service.IndexMainRecService;

@RestController
@RequestMapping("/api/blog")
public class BlogController {
	
	@Autowired
	private BlogService blogBervice;
	@Autowired
	private IndexMainRecService indexMainRecService;
	@Autowired
	private BlogTodayRecService blogTodayRecService;
	@Autowired
	private BlogStarService blogStarService;

	/**
	 * Code:
	 * 0-正确
	 * 1-ID is Empty
	 * 2-Service Error*/
	@ResponseBody
	@RequestMapping(value = "/getbyblogid", method = RequestMethod.GET)
	public BlogResponse GetBlogById(@RequestParam long blogid) {
		BlogResponse result = new BlogResponse();
		if(blogid == 0) {
			result.setCode(1);
		}else {
			BlogResponse temp = this.blogBervice.FindBlogById(blogid);
			if(temp == null) {
				result.setCode(2);
			}else {
				result = temp;
				result.setCode(0);
			}
		}
		return result;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-ID is Empty
	 * 2-Service Error
	 * 3-Title Or Content is Empty*/
	@ResponseBody
	@RequestMapping(value = "/addblog", method = RequestMethod.GET)
	public BlogResponse AddBlog(@RequestParam String title, 
						@RequestParam String content, 
						@RequestParam long authorId) {
		BlogResponse blogResponse = new BlogResponse();
		BlogResponse serviceResponse = new BlogResponse();
		if(authorId ==0) {
			blogResponse.setCode(1);
		}else {
			if(content!=null && !content.equals("") && title!=null && !title.equals("")) {
				serviceResponse.setContent(content);
				serviceResponse.getBlogInfo().setAuthorid(authorId);
				serviceResponse.getBlogInfo().setCommentsnum(0);
				serviceResponse.getBlogInfo().setReadnum(0);
				serviceResponse.getBlogInfo().setTitle(title);
				Date date = new Date();
			    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				serviceResponse.getBlogInfo().setTime(simpleDateFormat.format(date));
				int result = this.blogBervice.SaveBlog(serviceResponse);
				if(result == 0) {
					blogResponse.setCode(0);
				}else {
					blogResponse.setCode(2);
				}
			}else {
				blogResponse.setCode(3);
			}
		}
		return blogResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-ID is Empty
	 * 2-Service Error*/
	@ResponseBody
	@RequestMapping(value = "/deleteblog", method = RequestMethod.GET)
	public BlogResponse DeleteBlog(@RequestParam long blogId) {
		BlogResponse blogResponse = new BlogResponse();
		if(blogId == 0) {
			blogResponse.setCode(1);
		}else {
			int result = this.blogBervice.DeleteBlog(blogId);
			if(result == 0) {
				blogResponse.setCode(0);
			}else {
				blogResponse.setCode(2);
			}
		}
		return blogResponse;
		
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-ID is Empty
	 * 2-Service Error*/
	@ResponseBody
	@RequestMapping(value = "/addreadnum", method = RequestMethod.GET)
	public BlogResponse AddReadNum(@RequestParam long blogId) {
		BlogResponse blogResponse = new BlogResponse();
		if(blogId == 0) {
			blogResponse.setCode(1);
		}else {
			int result = this.blogBervice.AddReadNum(blogId);
			if(result == 0) {
				blogResponse.setCode(0);
			}else {
				blogResponse.setCode(2);
			}
		}
		return blogResponse;
	}

	/**
	 * Code:
	 * 0-正确
	 * 1-BlogID is Empty
	 * 2-UserID is Empty
	 * 3-Service Error
	 * 4-Content is Empty*/
	@ResponseBody
	@RequestMapping(value = "/addcomment", method = RequestMethod.GET)
	public BlogResponse AddComment(@RequestParam long blogId,
						@RequestParam long userId,
						@RequestParam String content) {
		BlogResponse blogResponse = new BlogResponse();
		if(blogId ==0) {
			blogResponse.setCode(1);
		} else if(userId == 0) {
			blogResponse.setCode(2);
		} else if(content == null || content.equals("")) {
			blogResponse.setCode(4);
		} else {
			Date date = new Date();
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			int result = this.blogBervice.AddComments(blogId, userId, simpleDateFormat.format(date), content);
			if(result == 0) {
				blogResponse.setCode(0);
			}else {
				blogResponse.setCode(3);
			}
		}
		return blogResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-BlogID is Empty
	 * 3-Service Error
	 * 4-Content or Title is Empty*/
	@ResponseBody
	@RequestMapping(value = "/modifyblog", method = RequestMethod.GET)
	public BlogResponse ModifyBlog(@RequestParam long blogId,
						@RequestParam String title,
						@RequestParam String content) {
		BlogResponse blogResponse = new BlogResponse();
		if(blogId == 0) {
			blogResponse.setCode(1);
		}else if(title == null || title.equals("")) {
			blogResponse.setCode(4);
		}else if(content == null || content.equals("")) {
			blogResponse.setCode(4);
		}else {
			int result = this.blogBervice.ModifyBlog(blogId, title, content);
			if(result == 0) {
				blogResponse.setCode(0);
			}else {
				blogResponse.setCode(3);
			}
		}
		return blogResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-title is Empty
	 * 2-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/findbytitle", method = RequestMethod.GET)
	public SearchResponse FindByTitle(@RequestParam String title, @RequestParam int page) {
		SearchResponse searchResponse = new SearchResponse();
		if(title == null || title.equals("")) {
			searchResponse.setCode(1);
		}else {
			List<BlogInfo> result = this.blogBervice.FindByTitle(title, page);
			if(result == null) {
				searchResponse.setCode(2);
			}else {
				int size = this.blogBervice.GetSizeOfSearch(title);
				if(size == -1) {
					searchResponse.setCode(-1);
				}else {
					searchResponse.setMaxNum(size);
					searchResponse.setCurrentPage(page);
					searchResponse.setCode(0);
					searchResponse.setResult(result);
				}
			}
		}
		return searchResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/getindexpicrecblogs", method = RequestMethod.GET)
	public PicRecommandResponse GetIndexPicRecommands() {
		PicRecommandResponse recommandResponse = new PicRecommandResponse();
		recommandResponse.setNum(4);
		PicRecommandResponse.PicRecItems[] items = this.blogBervice.GetIndexPicBlogRec();
		if(items == null){
			recommandResponse.setCode(1);
		}else {
			recommandResponse.setCode(0);
			recommandResponse.setItems(items);
		}
		return recommandResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/addindexpicrecblog", method = RequestMethod.GET)
	public PicRecommandResponse AddIndexPicRecommands(@RequestParam long blogid, 
			@RequestParam String title, MultipartFile pic, int seq) {
		PicRecommandResponse recommandResponse = new PicRecommandResponse();
		int result = this.blogBervice.AddIndexPicRecommand(blogid, title, pic, seq);
		if(result == 0) {
			recommandResponse.setCode(0);
		}else {
			recommandResponse.setCode(1);
		}
		return recommandResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/getindexmainrec", method = RequestMethod.GET)
	public BlogMainRecommandResponse GetIndexMainRecommands() {
		BlogMainRecommandResponse recommandResponse = new BlogMainRecommandResponse();
		List<IndexMainRecInfo> result = this.indexMainRecService.GetIndexRecs();
		if(result == null) {
			recommandResponse.setCode(1);
		}else {
			recommandResponse.setCode(0);
			recommandResponse.setIndexMainRecInfos(result);
		}
		return recommandResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/addindexmainrec", method = RequestMethod.GET)
	public BlogMainRecommandResponse AddIndexMainRecommands(@RequestParam long blogid, @RequestParam  String title,
										@RequestParam  String headline, @RequestParam  String time, @RequestParam  int read) {
		BlogMainRecommandResponse recommandResponse = new BlogMainRecommandResponse();
		int result = this.indexMainRecService.SaveIndexMainRec(blogid, title, headline, time, read);
		if(result != 0) {
			recommandResponse.setCode(1);
		}else {
			recommandResponse.setCode(0);
		}
		return recommandResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/deleteindexmainrec", method = RequestMethod.GET)
	public BlogMainRecommandResponse DeleteIndexMainRecommands(@RequestParam long recid) {
		BlogMainRecommandResponse recommandResponse = new BlogMainRecommandResponse();
		int result = this.indexMainRecService.DeleteIndexMainRec(recid);
		if(result != 0) {
			recommandResponse.setCode(1);
		}else {
			recommandResponse.setCode(0);
		}
		return recommandResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/addindexmainrecread", method = RequestMethod.GET)
	public BlogMainRecommandResponse AddIndexMainRecRead(@RequestParam long recid) {
		BlogMainRecommandResponse recommandResponse = new BlogMainRecommandResponse();
		int result = this.indexMainRecService.AddReadNum(recid);
		if(result != 0) {
			recommandResponse.setCode(1);
		}else {
			recommandResponse.setCode(0);
		}
		return recommandResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/addindextodayrec", method = RequestMethod.GET)
	public BlogTodayRecommandResponse AddIndexTodayRec(@RequestParam long blogid,
														@RequestParam String title,
														@RequestParam MultipartFile pic) {
		BlogTodayRecommandResponse blogTodayRecommandResponse =  new BlogTodayRecommandResponse();
		if(blogid == 0 || title == null || title.equals("") || pic == null) {
			blogTodayRecommandResponse.setCode(1);
		}else {
			int result = this.blogTodayRecService.AddBlogTodayRecItem(blogid, title, pic);
			if(result == 0) {
				blogTodayRecommandResponse.setCode(0);
			}else {
				blogTodayRecommandResponse.setCode(1);
			}
		}
		return blogTodayRecommandResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/deleteindextodayrec", method = RequestMethod.GET)
	public BlogTodayRecommandResponse DeleteIndexTodayRec(@RequestParam long blogid) {
		BlogTodayRecommandResponse blogTodayRecommandResponse =  new BlogTodayRecommandResponse();
		if(blogid == 0) {
			blogTodayRecommandResponse.setCode(1);
		}else {
			int result = this.blogTodayRecService.DeleteBlogTodayRecItem(blogid);
			if(result == 0) {
				blogTodayRecommandResponse.setCode(0);
			}else {
				blogTodayRecommandResponse.setCode(1);
			}
		}
		return blogTodayRecommandResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/getindextodayrec", method = RequestMethod.GET)
	public BlogTodayRecommandResponse GetIndexTodayRec(@RequestParam int num) {
		BlogTodayRecommandResponse blogTodayRecommandResponse =  new BlogTodayRecommandResponse();
		List<?> result = this.blogTodayRecService.FindBlogTodayRecItem(num);
		if(result != null) {
			blogTodayRecommandResponse.setCode(0);
			blogTodayRecommandResponse.setTodayRecItems(result);
		}else {
			blogTodayRecommandResponse.setCode(1);
		}
		return blogTodayRecommandResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/addpicrec", method = RequestMethod.GET)
	public PicRecommandResponse AddPicRec(@RequestParam long blogid, 
											@RequestParam String title, 
											@RequestParam MultipartFile pic, 
											@RequestParam int seq) {
		PicRecommandResponse picRecommandResponse =  new PicRecommandResponse();
		int result = this.blogBervice.AddPicRecommand(blogid, title, pic, seq);
		if(result!=0) {
			picRecommandResponse.setCode(1);
		}else {
			picRecommandResponse.setCode(0);
		}
		return picRecommandResponse;
	}
	
	/**
	 * Code:
	 * 1-正确
	 * 0-Error*/
	@ResponseBody
	@RequestMapping(value = "/getpicrec", method = RequestMethod.GET)
	public PicRecommandResponse GetPicRec() {
		PicRecommandResponse picRecommandResponse =  new PicRecommandResponse();
		PicRecItems[] result = this.blogBervice.GetPicBlogRec();
		if(result!=null) {
			picRecommandResponse.setCode(0);
			picRecommandResponse.setNum(4);
			picRecommandResponse.setItems(result);
		}else {
			picRecommandResponse.setCode(1);
		}
		return picRecommandResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/getmainrec", method = RequestMethod.GET)
	public BlogMainRecommandResponse GetMainRecommands() {
		BlogMainRecommandResponse recommandResponse = new BlogMainRecommandResponse();
		List<IndexMainRecInfo> result = this.indexMainRecService.GetIndexRecs();
		if(result == null) {
			recommandResponse.setCode(1);
		}else {
			recommandResponse.setCode(0);
			recommandResponse.setIndexMainRecInfos(result);
		}
		return recommandResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/getblogstar", method = RequestMethod.GET)
	public BlogStarResponse GetBlogStars() {
		BlogStarResponse blogStarResponse = new BlogStarResponse(); 
		List<?> result = this.blogStarService.GetBlogStar();
		if(result == null) {
			blogStarResponse.setCode(1);
		}else {
			blogStarResponse.setCode(0);
			blogStarResponse.setItems(result);
		}
		return blogStarResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/addblogstar", method = RequestMethod.POST)
	public BlogStarResponse AddBlogStars(@RequestParam long userid, @RequestParam String name,
			@RequestParam String des) {
		BlogStarResponse blogStarResponse = new BlogStarResponse(); 
		int result = this.blogStarService.AddBlogStar(userid, des, name);
		if(result != 0) {
			blogStarResponse.setCode(1);
		}else {
			blogStarResponse.setCode(0);
		}
		return blogStarResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/getbyauthor", method = RequestMethod.GET)
	public SearchResponse GetByAuthor(@RequestParam long authorid) {
		SearchResponse response = new SearchResponse(); 
		List<?> result = this.blogBervice.GetBlogByAuthor(authorid);
		response.setCode(0);
		response.setResult(result);
		return response;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/deleteblogstar", method = RequestMethod.POST)
	public BlogStarResponse DeleteBlogStars(@RequestParam long userid) {
		BlogStarResponse blogStarResponse = new BlogStarResponse(); 
		int result = this.blogStarService.DeleteBlogStar(userid);
		if(result != 0) {
			blogStarResponse.setCode(1);
		}else {
			blogStarResponse.setCode(0);
		}
		return blogStarResponse;
	}
	
	public BlogService getBlogBervice() {
		return blogBervice;
	}

	public void setBlogBervice(BlogService blogBervice) {
		this.blogBervice = blogBervice;
	}

	public IndexMainRecService getIndexMainRecService() {
		return indexMainRecService;
	}

	public void setIndexMainRecService(IndexMainRecService indexMainRecService) {
		this.indexMainRecService = indexMainRecService;
	}

	public BlogTodayRecService getBlogTodayRecService() {
		return blogTodayRecService;
	}

	public void setBlogTodayRecService(BlogTodayRecService blogTodayRecService) {
		this.blogTodayRecService = blogTodayRecService;
	}
	
	public BlogStarService getBlogStarService() {
		return blogStarService;
	}

	public void setBlogStarService(BlogStarService blogStarService) {
		this.blogStarService = blogStarService;
	}
}
