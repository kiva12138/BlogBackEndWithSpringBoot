package com.SunBlog.BlogSiteBackEnd.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SunBlog.BlogSiteBackEnd.Entity.ResInfo;

@Repository
public interface ResRepository extends JpaRepository<ResInfo, Integer>{
	ResInfo findByResid(long resid);
	void deleteByResid(long resid);
	@Modifying
	@Query(value="UPDATE ResInfo resinfo SET resinfo.downnum=(resinfo.downnum+:addnum) WHERE resinfo.resid=:resid")
	void addDownNum(@Param ("addnum")int addnum, @Param ("resid")long resid);
	@Modifying
	@Query(value="UPDATE ResInfo resinfo SET resinfo.commentsnum=(resinfo.commentsnum+:addnum) WHERE resinfo.resid=:resid")
	void addCommentNum(@Param ("addnum")int addnum, @Param ("resid")long resid);
	@Query("Select c from ResInfo c where c.title like %:title%")
	List<ResInfo> findByTitleLike(@Param ("title")String title);
	@Query("Select c from ResInfo c where c.authorid=:uid")
	List<ResInfo> findByAuthor(@Param ("uid")long uid);
}
