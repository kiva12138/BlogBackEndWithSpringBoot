package com.SunBlog.BlogSiteBackEnd.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SunBlog.BlogSiteBackEnd.Entity.HotQuesLifeInfo;

@Repository
public interface HotQuesLifeRepository extends JpaRepository<HotQuesLifeInfo, Integer>{
	@Modifying
	@Query(value="UPDATE HotQuesLifeInfo hotQuesTechInfo SET hotQuesTechInfo.joinnum=(hotQuesTechInfo.joinnum+1) WHERE hotQuesTechInfo.quesid=:quesid")
	void addJoinNum(@Param ("quesid")long quesid);
	
	void deleteByQuesid(long quesid);
}
