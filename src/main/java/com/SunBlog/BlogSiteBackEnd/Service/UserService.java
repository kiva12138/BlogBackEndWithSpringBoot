package com.SunBlog.BlogSiteBackEnd.Service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.SunBlog.BlogSiteBackEnd.Entity.User;
import com.SunBlog.BlogSiteBackEnd.Repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	
	@Autowired
	public UserService (UserRepository userRepository) {
		this.setUserRepository(userRepository);
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public User FindUserByUid(long uid) {
		if (uid == 0) {
			System.out.println("Error in UserService, UId is 0");
			return null;
		} else {
			User user = this.userRepository.findByUid(uid);
			if (user == null) {
				System.out.println("Error in UserService, Cannot find User By UID");
				return null;
			} else {
				return user;
			}
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public User FindUserByUidAndPassword(long uid, String password) {
		if(uid == 0 || password == null || password.equals("")) {
			System.out.println("Errot in UserService, UID or Password is empty");
			return null;
		}else {
			User user = this.userRepository.findByUidAndPassword(uid, password);
			if (user == null) {
				System.out.println("Error in UserService, Cannot find User By UID and Password, Password may be wrong");
				return null;
			}else {
				return user;
			}
		}
	}
	
	/**
	 * Return:
	 * id-正确
	 * 0-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public long saveUser(User user) {
		if(user == null) {
			System.out.println("Cannot Save User Because User is null");
			return 0;
		}
		long result = this.userRepository.saveAndFlush(user).getUid();
		return result;
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateGeneralInfo(long uid, String username, String birth, String phone) {
		if(uid == 0 ||
				username == null || username.equals("") ||
				birth == null || birth.equals("") ||
				phone == null || phone.equals("")) {
			return 1;
		}else {
			this.userRepository.updateGeneralInfo(phone, username, birth, uid);
			return 0;
		}
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int updatePassword(long uid, String password) {
		if(uid == 0 || password == null || password.equals("") ) {
			return 1;
		}else {
			this.userRepository.updatePassword(password, uid);
			return 0;
		}
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int saveHead(long userid, MultipartFile head) {
		if(userid <= 0 || head == null) {
			return 1;
		}else {
			try {
				File file = new File(ResourceUtils.getURL("classpath:").getPath()+"User/Head/"+userid);
				if(file.exists()) {
					if(!file.delete()) {
						return 2;
					}
				}
				file.mkdirs();
				file = new File(ResourceUtils.getURL("classpath:").getPath()+"User/Head/"+userid+"/head");
				file.createNewFile();
				head.transferTo(file);
				return 0;
			} catch (Exception e) {
				System.out.println("Cannot Save Files");
				return 1;
			}
		}
	}
}
