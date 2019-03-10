package com.SunBlog.BlogSiteBackEnd.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SunBlog.BlogSiteBackEnd.Entity.BlogInfo;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogInfo, Integer>{
	BlogInfo findByBlogid(long blogid);
	@Modifying
	@Query(value="UPDATE BlogInfo bloginfo SET bloginfo.readnum=(bloginfo.readnum+:addnum) WHERE bloginfo.blogid=:blogid")
	void addReadNum(@Param ("addnum")int addnum, @Param ("blogid")long blogid);
	@Modifying
	@Query(value="UPDATE BlogInfo bloginfo SET bloginfo.commentsnum=(bloginfo.commentsnum+:addnum) WHERE bloginfo.blogid=:blogid")
	void addCommentsNum(@Param ("addnum")int addnum, @Param ("blogid")long blogid);
	void deleteByBlogid(long blogid);
	@Query("Select c from BlogInfo c where c.title like %:title%")
	List<BlogInfo> findByTitleLike(@Param ("title")String title);
	@Query("Select c from BlogInfo c where c.authorid=:uid")
	List<BlogInfo> findByAuthor(@Param ("uid")long uid);
}
