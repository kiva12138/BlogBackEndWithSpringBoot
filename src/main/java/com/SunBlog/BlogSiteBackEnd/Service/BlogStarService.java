package com.SunBlog.BlogSiteBackEnd.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.SunBlog.BlogSiteBackEnd.Entity.BlogStarInfo;
import com.SunBlog.BlogSiteBackEnd.Repository.BlogStarRepository;

@Service
public class BlogStarService {
	@Autowired
	BlogStarRepository blogStarRepository;

	public BlogStarRepository getBlogStarRepository() {
		return blogStarRepository;
	}

	public void setBlogStarRepository(BlogStarRepository blogStarRepository) {
		this.blogStarRepository = blogStarRepository;
	}
	
	/**
	 * Return:
	 * 0 -正确
	 * 1 -File Error*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int AddBlogStar(long userid, String des, String name) {
		if(userid == 0||des==null || des.equals("") || name==null || name.equals("")) {
			System.out.println("The Info is Empty");
			return 1;
		}else {
			BlogStarInfo blogStarInfo = new BlogStarInfo();
			blogStarInfo.setName(name);
			blogStarInfo.setDes(des);
			blogStarInfo.setUserid(userid);
			this.blogStarRepository.save(blogStarInfo);
			return 0;
		}
	}
	
	/**
	 * Return:
	 * 0 -正确
	 * 1 -File Error*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int DeleteBlogStar(long userid) {
		if(userid == 0) {
			System.out.println("The Info is Empty");
			return 1;
		}else {
			this.blogStarRepository.deleteByUserid(userid);
			return 0;
		}
	}
	
	/**
	 * Return:
	 * 0 -正确
	 * 1 -File Error*/
	@Transactional(propagation = Propagation.REQUIRED)
	public List<?> GetBlogStar() {
		List<BlogStarInfo> blogStarInfos = this.blogStarRepository.findAll();
		if(blogStarInfos == null) {
			System.out.println("The Info is Empty");
		}
		return blogStarInfos;
	}

}
