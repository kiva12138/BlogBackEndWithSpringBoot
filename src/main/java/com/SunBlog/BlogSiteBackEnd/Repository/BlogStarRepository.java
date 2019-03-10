package com.SunBlog.BlogSiteBackEnd.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SunBlog.BlogSiteBackEnd.Entity.BlogStarInfo;

public interface BlogStarRepository extends JpaRepository<BlogStarInfo, Integer>{
	void deleteByUserid(long userid);
}
