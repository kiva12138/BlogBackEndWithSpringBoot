package com.SunBlog.BlogSiteBackEnd.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SunBlog.BlogSiteBackEnd.Entity.ResHotInfo;

@Repository
public interface ResHotRepository extends JpaRepository<ResHotInfo, Integer>{
	void deleteByResid(long resid);
	@Modifying
	@Query(value="UPDATE ResHotInfo resHotInfo SET resHotInfo.downnum=(resHotInfo.downnum+1) WHERE resHotInfo.resid=:resid")
	void addDownNum(@Param ("resid")long resid);
}
