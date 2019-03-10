package com.SunBlog.BlogSiteBackEnd.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SunBlog.BlogSiteBackEnd.Entity.ResNewInfo;

@Repository
public interface ResNewRepository extends JpaRepository<ResNewInfo, Integer>{
	void deleteByResid(long resid);
	@Modifying
	@Query(value="UPDATE ResNewInfo resNewInfo SET resNewInfo.downnum=(resNewInfo.downnum+1) WHERE resNewInfo.resid=:resid")
	void addDownNum(@Param ("resid")long resid);
}
