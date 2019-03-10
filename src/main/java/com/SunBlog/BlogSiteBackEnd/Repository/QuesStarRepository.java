package com.SunBlog.BlogSiteBackEnd.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SunBlog.BlogSiteBackEnd.Entity.QuesStarInfo;

@Repository
public interface QuesStarRepository extends JpaRepository<QuesStarInfo, Integer>{
	void deleteByUserid(long userid);
}
