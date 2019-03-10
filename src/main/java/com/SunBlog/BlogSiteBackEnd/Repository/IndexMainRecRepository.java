package com.SunBlog.BlogSiteBackEnd.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SunBlog.BlogSiteBackEnd.Entity.IndexMainRecInfo;

@Repository
public interface IndexMainRecRepository extends JpaRepository<IndexMainRecInfo, Integer>{
	void deleteByRecid(long recid);
	@Modifying
	@Query(value="UPDATE IndexMainRecInfo info SET info.readnum=(info.readnum+:addnum) WHERE info.recid=:recid")
	void addReadNum(@Param ("addnum")int addnum, @Param ("recid")long recid);
}
