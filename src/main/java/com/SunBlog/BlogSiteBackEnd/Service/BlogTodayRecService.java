package com.SunBlog.BlogSiteBackEnd.Service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.SunBlog.BlogSiteBackEnd.Entity.BlogTodayRecInfo;
import com.SunBlog.BlogSiteBackEnd.Repository.BlogTodayRecRepository;

@Service
public class BlogTodayRecService {
	@Autowired
	private BlogTodayRecRepository blogTodayRecRepository;

	public BlogTodayRecRepository getBlogTodayRecRepository() {
		return blogTodayRecRepository;
	}

	public void setBlogTodayRecRepository(BlogTodayRecRepository blogTodayRecRepository) {
		this.blogTodayRecRepository = blogTodayRecRepository;
	}

	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int AddBlogTodayRecItem(long blogid, String title, MultipartFile pic) {
		if(blogid == 0 || title == null || title.equals("") || pic == null) {
			return 1;
		}else {
			BlogTodayRecInfo blogTodayRecInfo = new BlogTodayRecInfo();
			blogTodayRecInfo.setBlogid(blogid);
			blogTodayRecInfo.setTitle(title);
			try {
				blogTodayRecInfo.setPicurl(ResourceUtils.getURL("classpath:").getPath()+"Blogs/TodayRecommand/"+blogid+"/pic");
				this.blogTodayRecRepository.saveAndFlush(blogTodayRecInfo);				
				File fileToSave;
				fileToSave = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/TodayRecommand/"+blogid);
				if(!fileToSave.exists()) {
					fileToSave.mkdirs();
				}
				fileToSave = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/TodayRecommand/"+blogid+"/pic");
				fileToSave.createNewFile();
				pic.transferTo(fileToSave);
			} catch (Exception e) {
				System.out.println("Cannot Save Blog Pics");
				return 1;
			}
			return 0;
		}
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int DeleteBlogTodayRecItem(long blogid) {
		if(blogid == 0) {
			return 1;
		}else {
			try {
				this.blogTodayRecRepository.deleteByBlogid(blogid);
				File fileToDelete;
				fileToDelete = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/TodayRecommand/"+blogid+"/pic");
				if(!fileToDelete.exists()) {
					return 1;
				}
				fileToDelete.delete();
				fileToDelete = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/TodayRecommand/"+blogid);
				fileToDelete.delete();
			} catch (Exception e) {
				System.out.println("Cannot Delete Blog Pics");
				return 1;
			}
			return 0;
		}
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public List<?> FindBlogTodayRecItem(int num) {
		List<BlogTodayRecInfo> blogTodayRecInfos = this.blogTodayRecRepository.findAll();
		if(blogTodayRecInfos == null) {
			System.out.println("Cannot Get Todat Rec From DB");
		}else {
			blogTodayRecInfos = blogTodayRecInfos.subList(0, num);
		}
		return blogTodayRecInfos;
	}
}
