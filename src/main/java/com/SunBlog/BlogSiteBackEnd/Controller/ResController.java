package com.SunBlog.BlogSiteBackEnd.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SunBlog.BlogSiteBackEnd.Entity.ResInfo;
import com.SunBlog.BlogSiteBackEnd.Reponse.ResRecResponse;
import com.SunBlog.BlogSiteBackEnd.Reponse.ResResponse;
import com.SunBlog.BlogSiteBackEnd.Reponse.ResStarResponse;
import com.SunBlog.BlogSiteBackEnd.Reponse.SearchResponse;
import com.SunBlog.BlogSiteBackEnd.Service.ResRecService;
import com.SunBlog.BlogSiteBackEnd.Service.ResService;
import com.SunBlog.BlogSiteBackEnd.Service.ResStarService;

@RestController
@RequestMapping("/api/res")
public class ResController {
	
	@Autowired
	private ResService resService;
	
	@Autowired
	private ResStarService resStarService;
	
	@Autowired
	private ResRecService resRecService;
	
	public ResRecService getResRecService() {
		return resRecService;
	}

	public void setResRecService(ResRecService resRecService) {
		this.resRecService = resRecService;
	}
	
	public ResStarService getResStarService() {
		return resStarService;
	}

	public void setResStarService(ResStarService resStarService) {
		this.resStarService = resStarService;
	}

	public ResService getResService() {
		return resService;
	}

	public void setResService(ResService resService) {
		this.resService = resService;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-ID is Empty
	 * 2-Service Error*/
	@ResponseBody
	@RequestMapping(value = "/getbyresid", method = RequestMethod.GET)
	public ResResponse GetResById(@RequestParam long resid) {
		ResResponse result = new ResResponse();
		if(resid == 0) {
			result.setCode(1);
		}else {
			ResResponse temp = this.resService.FindResById(resid);
			if(temp == null) {
				result.setCode(2);
			}else {
				result = temp;
				result.setCode(0);
			}
		}
		return result;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-ID is Empty
	 * 2-Service Error
	 * 3-Title Or Content Or Format Or File is Empty*/
	@ResponseBody
	@RequestMapping(value = "/addres", method = RequestMethod.POST)
	public ResResponse AddRes(@RequestParam String title, 
						@RequestParam String descript, 
						@RequestParam long authorId,
						@RequestParam String format,
						@RequestParam("file") MultipartFile file) {
		ResResponse resResponse = new ResResponse();
		ResResponse serviceResponse = new ResResponse();
		if(authorId ==0) {
			resResponse.setCode(1);
		}else {
			if(descript!=null && !descript.equals("") 
					&& title!=null && !title.equals("") 
					&& format!=null && !format.equals("")
					&& !file.isEmpty()) {
				serviceResponse.setDescription(descript);
				serviceResponse.getResInfo().setAuthorid(authorId);
				serviceResponse.getResInfo().setDownnum(0);
				serviceResponse.getResInfo().setCommentsnum(0);
				serviceResponse.getResInfo().setFormat(format);
				serviceResponse.getResInfo().setTitle(title);
				Date date = new Date();
			    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				serviceResponse.getResInfo().setTime(simpleDateFormat.format(date));
				String fileName = file.getOriginalFilename();
	        	serviceResponse.getResInfo().setFilename(fileName);
				int result = this.resService.SaveRes(serviceResponse, file);
				if(result != 0) {
					resResponse.setCode(2);
				}else {
					 resResponse.setCode(0);
				}
			}else {
				resResponse.setCode(3);
			}
		}
		return resResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-ID is Empty
	 * 2-Service Error*/
	@ResponseBody
	@RequestMapping(value = "/deleteres", method = RequestMethod.GET)
	public ResResponse DeleteRes(@RequestParam long resid) {
		ResResponse resResponse = new ResResponse();
		if(resid == 0) {
			resResponse.setCode(1);
		}else {
			int result = this.resService.DeleteRes(resid);
			if(result == 0) {
				resResponse.setCode(0);
			}else {
				resResponse.setCode(2);
			}
		}
		return resResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-ResID is Empty
	 * 2-UserID is Empty
	 * 3-Service Error
	 * 4-Content is Empty*/
	@ResponseBody
	@RequestMapping(value = "/addcomment", method = RequestMethod.GET)
	public ResResponse AddComment(@RequestParam long resid,
						@RequestParam long userid,
						@RequestParam String username,
						@RequestParam String content) {
		ResResponse resResponse = new ResResponse();
		if(resid ==0) {
			resResponse.setCode(1);
		} else if(userid == 0) {
			resResponse.setCode(2);
		} else if(content == null || content.equals("")) {
			resResponse.setCode(4);
		} else {
			Date date = new Date();
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			int result = this.resService.AddComment(resid, userid, username, simpleDateFormat.format(date), content);
			if(result == 0) {
				resResponse.setCode(0);
			}else {
				resResponse.setCode(3);
			}
		}
		return resResponse;
	}
	
	/**
	 * Code:
	 * Null-Error
	 * File-Success*/
	@ResponseBody
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> Download(@RequestParam long resid) {
		ResponseEntity<InputStreamResource> response = this.resService.Download(resid);
		return response;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-title is Empty
	 * 2-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/findbytitle", method = RequestMethod.GET)
	public SearchResponse FindByTitle(@RequestParam String title, @RequestParam int page) {
		SearchResponse searchResponse = new SearchResponse();
		if(title == null || title.equals("")) {
			searchResponse.setCode(1);
		}else {
			List<ResInfo> result = this.resService.FindByTitle(title, page);
			if(result == null) {
				searchResponse.setCode(2);
			}else {
				int size = this.resService.GetSizeOfSearch(title);
				if(size == -1) {
					searchResponse.setCode(-1);
				}else {
					searchResponse.setMaxNum(size);
					searchResponse.setCurrentPage(page);
					searchResponse.setCode(0);
					searchResponse.setResult(result);
				}
			}
		}
		return searchResponse;
	}
	
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/getresstar", method = RequestMethod.GET)
	public ResStarResponse GetResStars() {
		ResStarResponse resStarResponse = new ResStarResponse(); 
		List<?> result = this.resStarService.GetResStar();
		if(result == null) {
			resStarResponse.setCode(1);
		}else {
			resStarResponse.setCode(0);
			resStarResponse.setItems(result);
		}
		return resStarResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/addresstar", method = RequestMethod.GET)
	public ResStarResponse AddResStars(@RequestParam long userid, @RequestParam String name, @RequestParam int resnum) {
		ResStarResponse resStarResponse = new ResStarResponse(); 
		int result = this.resStarService.AddResStar(userid,name,resnum);
		if(result != 0) {
			resStarResponse.setCode(1);
		}else {
			resStarResponse.setCode(0);
		}
		return resStarResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/deleteresstar", method = RequestMethod.GET)
	public ResStarResponse DeleteResStars(@RequestParam long userid) {
		ResStarResponse resStarResponse = new ResStarResponse(); 
		int result = this.resStarService.DeleteResStar(userid);
		if(result != 0) {
			resStarResponse.setCode(1);
		}else {
			resStarResponse.setCode(0);
		}
		return resStarResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/addresstarresnum", method = RequestMethod.GET)
	public ResStarResponse AddResStarsResNum(@RequestParam long userid) {
		ResStarResponse resStarResponse = new ResStarResponse(); 
		if(userid > 0) {
			this.resStarService.AddResStarResNum(userid);
			resStarResponse.setCode(0);
		}else {
			resStarResponse.setCode(1);
		}
		return resStarResponse;
	}

	/**
	 * Code:
	 * 0-正确
	 * 1-Params is Empty
	 * 2-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/addhotres", method = RequestMethod.GET)
	public ResRecResponse AddHotRes(@RequestParam long resid, @RequestParam String title,
										@RequestParam String format, @RequestParam int downnum, @RequestParam String time) {
		ResRecResponse resRecResponse = new ResRecResponse();
		if(title == null || title.equals("") || resid == 0 || format == null || format.equals("")
				|| time == null || time.equals("")) {
			resRecResponse.setCode(1);
		}else {
			int result = this.resRecService.SaveResHot(resid, title, format, downnum, time);
			if(result == 0) {
				resRecResponse.setCode(0);
			}else {
				resRecResponse.setCode(2);
			}
		}
		return resRecResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Params is Empty
	 * 2-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/deletehotres", method = RequestMethod.GET)
	public ResRecResponse DeleteHotRes(@RequestParam long resid) {
		ResRecResponse resRecResponse = new ResRecResponse();
		if(resid == 0) {
			resRecResponse.setCode(1);
		}else {
			int result = this.resRecService.DeleteResHot(resid);
			if(result == 0) {
				resRecResponse.setCode(0);
			}else {
				resRecResponse.setCode(2);
			}
		}
		return resRecResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Params is Empty
	 * 2-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/addhotresdownnum", method = RequestMethod.GET)
	public ResRecResponse AddHotResDownNum(@RequestParam long resid) {
		ResRecResponse recResponse = new ResRecResponse();
		if(resid == 0) {
			recResponse.setCode(1);
		}else {
			int result = this.resRecService.AddResHotDownNum(resid);
			if(result == 0) {
				recResponse.setCode(0);
			}else {
				recResponse.setCode(2);
			}
		}
		return recResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/gethotres", method = RequestMethod.GET)
	public ResRecResponse GetHotRes() {
		ResRecResponse response = new ResRecResponse();
		List<?> result = this.resRecService.GetResHot();
		if(result == null) {
			response.setCode(1);
		}else {
			response.setCode(0);
			response.setItems(result);
		}
		return response;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Params is Empty
	 * 2-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/addnewres", method = RequestMethod.GET)
	public ResRecResponse AddNewRes(@RequestParam long resid, @RequestParam String title,
										@RequestParam String format, @RequestParam int downnum, @RequestParam String time) {
		ResRecResponse resRecResponse = new ResRecResponse();
		if(title == null || title.equals("") || resid == 0 || format == null || format.equals("")
				|| time == null || time.equals("")) {
			resRecResponse.setCode(1);
		}else {
			int result = this.resRecService.SaveResNew(resid, title, format, downnum, time);
			if(result == 0) {
				resRecResponse.setCode(0);
			}else {
				resRecResponse.setCode(2);
			}
		}
		return resRecResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Params is Empty
	 * 2-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/deletenewres", method = RequestMethod.GET)
	public ResRecResponse DeleteNewRes(@RequestParam long resid) {
		ResRecResponse resRecResponse = new ResRecResponse();
		if(resid == 0) {
			resRecResponse.setCode(1);
		}else {
			int result = this.resRecService.DeleteResNew(resid);
			if(result == 0) {
				resRecResponse.setCode(0);
			}else {
				resRecResponse.setCode(2);
			}
		}
		return resRecResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Params is Empty
	 * 2-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/addnewresdownnum", method = RequestMethod.GET)
	public ResRecResponse AddNewResDownNum(@RequestParam long resid) {
		ResRecResponse recResponse = new ResRecResponse();
		if(resid == 0) {
			recResponse.setCode(1);
		}else {
			int result = this.resRecService.AddResNewDownNum(resid);
			if(result == 0) {
				recResponse.setCode(0);
			}else {
				recResponse.setCode(2);
			}
		}
		return recResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/getnewres", method = RequestMethod.GET)
	public ResRecResponse GetNewRes() {
		ResRecResponse response = new ResRecResponse();
		List<?> result = this.resRecService.GetResNew();
		if(result == null) {
			response.setCode(1);
		}else {
			response.setCode(0);
			response.setItems(result);
		}
		return response;
	}

	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/getbyauthor", method = RequestMethod.GET)
	public SearchResponse GetByAuthor(@RequestParam long authorid) {
		SearchResponse response = new SearchResponse(); 
		List<?> result = this.resService.GetResByAuthor(authorid);
		response.setCode(0);
		response.setResult(result);
		return response;
	}
}
