package com.SunBlog.BlogSiteBackEnd.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SunBlog.BlogSiteBackEnd.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	User findByUid(long uid);
	User findByUidAndPassword(long uid, String password);
	@Modifying
	@Query(value="UPDATE User user SET user.phone=:phone , user.username=:username , user.birth=:birth WHERE user.uid=:uid")
	int updateGeneralInfo(@Param ("phone")String phone, 
							@Param ("username")String username,
							@Param ("birth")String birth,
							@Param ("uid") long uid);
	@Modifying
	@Query(value="UPDATE User user SET user.password=:password WHERE user.uid=:uid")
	int updatePassword(@Param ("password")String password,
							@Param ("uid") long uid);
	@Modifying
	@Query(value="UPDATE User user SET user.blognum=(user.blognum+1) WHERE user.uid=:uid")
	int addBlog(@Param ("uid") long uid);
	
	@Modifying
	@Query(value="UPDATE User user SET user.quesnum=(user.quesnum+1) WHERE user.uid=:uid")
	int addQues(@Param ("uid") long uid);
	
	@Modifying
	@Query(value="UPDATE User user SET user.resnum=(user.resnum+1) WHERE user.uid=:uid")
	int addRes(@Param ("uid") long uid);
	
}
