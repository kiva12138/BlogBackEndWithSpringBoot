package com.SunBlog.BlogSiteBackEnd.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SunBlog.BlogSiteBackEnd.Entity.BlogTodayRecInfo;

public interface BlogTodayRecRepository extends JpaRepository<BlogTodayRecInfo, Integer> {
	void deleteByBlogid(long blogid);
}
