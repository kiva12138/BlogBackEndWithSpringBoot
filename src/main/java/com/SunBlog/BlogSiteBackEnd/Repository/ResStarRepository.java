package com.SunBlog.BlogSiteBackEnd.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SunBlog.BlogSiteBackEnd.Entity.ResStarInfo;

@Repository
public interface ResStarRepository extends JpaRepository<ResStarInfo, Integer>{
	void deleteByUserid(long userid);
	@Modifying
	@Query(value="UPDATE ResStarInfo r SET r.resnum=(r.resnum+1) WHERE r.userid=:userid")
	void addResNum(@Param ("userid")long userid);
}
