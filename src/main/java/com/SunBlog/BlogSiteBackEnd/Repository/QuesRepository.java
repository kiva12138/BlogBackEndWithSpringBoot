package com.SunBlog.BlogSiteBackEnd.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SunBlog.BlogSiteBackEnd.Entity.QuesInfo;

@Repository
public interface QuesRepository extends JpaRepository<QuesInfo, Integer> {
	QuesInfo findByQuesid(long quesid);
	void deleteByQuesid(long quesid);
	@Modifying
	@Query(value="UPDATE QuesInfo quesinfo SET quesinfo.ansnum=(quesinfo.ansnum+:addnum) WHERE quesinfo.quesid=:quesid")
	void addAnsNum(@Param ("addnum")int addnum, @Param ("quesid")long quesid);
	@Query("Select c from QuesInfo c where c.title like %:title%")
	List<QuesInfo> findByTitleLike(@Param ("title")String title);
	@Query("Select c from QuesInfo c where c.authorid=:uid")
	List<QuesInfo> findByAuthor(@Param ("uid")long uid);
}
