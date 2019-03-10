package com.SunBlog.BlogSiteBackEnd.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SunBlog.BlogSiteBackEnd.Entity.User;
import com.SunBlog.BlogSiteBackEnd.Reponse.UserResponse;
import com.SunBlog.BlogSiteBackEnd.Service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private User user;
	private UserResponse responsebody;
	
	@Autowired
	private UserService userService;
	
	public UserController () {
		this.setUser(new User());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * code:
	 * 		0-无错误
	 * 		1-UID为空
	 * 		2-从Service中获取不到数据
	 * user:用户数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/getbyuid", method = RequestMethod.GET)
	public UserResponse GetUserByUid(@RequestParam long uid) {
		this.responsebody = new UserResponse();
		if(uid == 0) {
			System.out.println("Error in UserController, Uid is Empty");
			responsebody.setCode(1);
			
		}else {
			User user = this.userService.FindUserByUid(uid);
			if(user == null) {
				responsebody.setCode(2);
				System.out.println("Error in UserController, Cannot Get User By UID");
			}else {
				responsebody.setCode(0);
				responsebody.setUser(user);
			}
		}
		return this.responsebody;
	}
	
	/**
	 * code:
	 * 		0-无错误
	 * 		1-UID为空
	 * 		2-从Service中获取不到数据
	 * 
	 * user:用户数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/getbyuidandpassword", method = RequestMethod.GET)
	public UserResponse GetUserByUidAndPassword(@RequestParam long uid, @RequestParam String password) {
		this.responsebody = new UserResponse();
		if(uid == 0 || password == null || password.equals("")) {
			System.out.println("Error in UserController, Uid or Password is Empty");
			responsebody.setCode(1);
			
		}else {
			User user = this.userService.FindUserByUidAndPassword(uid, password);
			if(user == null) {
				responsebody.setCode(2);
				System.out.println("Error in UserController, Cannot Get User By UID And Password");
			}else {
				responsebody.setCode(0);
				responsebody.setUser(user);
			}
		}
		return this.responsebody;
	}
	
	/**
	 * code:
	 * 		0-无错误
	 * 		1-Name为空
	 * 		2-Password为空
	 * 		3-Phone为空
	 * 		4-birth为空
	 * 		5-从Service中得到错误
	 * */
	@ResponseBody
	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public UserResponse AddUser(
								@RequestParam String username,
								@RequestParam String password,
								@RequestParam String phone,
								@RequestParam String birth,
								@RequestParam MultipartFile head) {
		this.responsebody = new UserResponse();
		if(username == null || username.equals("")) {
			this.responsebody.setCode(0);
			return this.responsebody;
		} else if(password == null || password.equals("")) {
			this.responsebody.setCode(2);
			return this.responsebody;
		} else if(phone == null || phone.equals("")) {
			this.responsebody.setCode(3);
			return this.responsebody;
		} else if(birth == null || birth.equals("")) {
			this.responsebody.setCode(4);
			return this.responsebody;
		} else {
			this.user = new User();
			this.user.setUsername(username);
			this.user.setPassword(password);
			this.user.setPhone(phone);
			this.user.setBirth(birth);
			this.user.setBlognum(0);
			this.user.setQuesnum(0);
			this.user.setResnum(0);
			String time="";
		    Date date = new Date();
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    time = simpleDateFormat.format(date);
		    this.user.setRegtime(time);
			long result = this.userService.saveUser(this.user);
			if(result == 0) {
				this.responsebody.setCode(5);
				return this.responsebody;
			}else {
				int result2 = this.userService.saveHead(result, head);
				if(result2 == 0) {
					this.responsebody.setCode(0);
				}else {
					this.responsebody.setCode(5);
				}
				return this.responsebody;
			}
		}
	}
	
	
	/**
	 * code:
	 * 		0-无错误
	 * 		1-Name为空
	 * 		3-Phone为空
	 * 		4-birth为空
	 * 		5-uid为空
	 * 		6-从Service中得到错误
	 * */
	@ResponseBody
	@RequestMapping(value = "/updategeneral", method = RequestMethod.GET)
	public UserResponse UpdateGeneralInfo(@RequestParam long uid,
			@RequestParam String username,
			@RequestParam String phone,
			@RequestParam String birth) {
		this.responsebody = new UserResponse();
		if(username == null || username.equals("")) {
			this.responsebody.setCode(0);
			return this.responsebody;
		} else if(phone == null || phone.equals("")) {
			this.responsebody.setCode(3);
			return this.responsebody;
		} else if(birth == null || birth.equals("")) {
			this.responsebody.setCode(4);
			return this.responsebody;
		} else if(uid == 0){
			this.responsebody.setCode(5);
			return this.responsebody;
		}else {
			int result = this.userService.updateGeneralInfo(uid, username, birth, phone);
			if(result == 1) {
				this.responsebody.setCode(6);
				return this.responsebody;
			}else {
				this.responsebody.setCode(0);
				return this.responsebody;
			}
		}
	}
	
	/**
	 * code:
	 * 		0-无错误
	 * 		1-UID为空
	 * 		2-Password为空
	 * 		3-Service内部错误
	 * */
	@ResponseBody
	@RequestMapping(value = "/updatepassword", method = RequestMethod.GET)
	public UserResponse UpdatePassword(@RequestParam long uid,
			@RequestParam String password) {
		this.responsebody = new UserResponse();
		if(uid ==0) {
			this.responsebody.setCode(1);
			return this.responsebody;
		} else if(password == null || password.equals("")) {
			this.responsebody.setCode(2);
			return this.responsebody;
		} else {
			int result = this.userService.updatePassword(uid, password);
			if(result == 0) {
				this.responsebody.setCode(0);
			}else {
				this.responsebody.setCode(3);
			}
			return this.responsebody;
		}
	}
}
