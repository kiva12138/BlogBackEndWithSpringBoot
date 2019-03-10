package com.SunBlog.BlogSiteBackEnd.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.SunBlog.BlogSiteBackEnd.Entity.QuesInfo;
import com.SunBlog.BlogSiteBackEnd.Reponse.HotQuesResponse;
import com.SunBlog.BlogSiteBackEnd.Reponse.QuesResponse;
import com.SunBlog.BlogSiteBackEnd.Reponse.QuesStarResponse;
import com.SunBlog.BlogSiteBackEnd.Reponse.SearchResponse;
import com.SunBlog.BlogSiteBackEnd.Service.HotQuesService;
import com.SunBlog.BlogSiteBackEnd.Service.QuesService;
import com.SunBlog.BlogSiteBackEnd.Service.QuesStarService;

@RestController
@RequestMapping("/api/ques")
public class QuesController {

	@Autowired
	private QuesService quesService;
	@Autowired
	private HotQuesService hotQuesService;
	@Autowired
	private QuesStarService quesStarService;

	public QuesStarService getQuesStarService() {
		return quesStarService;
	}

	public void setQuesStarService(QuesStarService quesStarService) {
		this.quesStarService = quesStarService;
	}
	
	public HotQuesService getHotQuesService() {
		return hotQuesService;
	}

	public void setHotQuesService(HotQuesService hotQuesService) {
		this.hotQuesService = hotQuesService;
	}

	public QuesService getQuesService() {
		return quesService;
	}

	public void setQuesService(QuesService quesService) {
		this.quesService = quesService;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-ID is Empty
	 * 2-Service Error*/
	@ResponseBody
	@RequestMapping(value = "/getbyquesid", method = RequestMethod.GET)
	public QuesResponse GetQuesById(@RequestParam long quesid) {
		QuesResponse result = new QuesResponse();
		if(quesid == 0) {
			result.setCode(1);
		}else {
			QuesResponse temp = this.quesService.FindQuesById(quesid);
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
	 * 3-Title Or Content is Empty*/
	@ResponseBody
	@RequestMapping(value = "/addques", method = RequestMethod.GET)
	public QuesResponse AddQues(@RequestParam String title, 
						@RequestParam String content, 
						@RequestParam long authorId,
						@RequestParam String type) {
		QuesResponse quesResponse = new QuesResponse();
		QuesResponse serviceResponse = new QuesResponse();
		if(authorId ==0) {
			quesResponse.setCode(1);
		}else {
			if(content!=null && !content.equals("") && title!=null && !title.equals("")) {
				serviceResponse.setContent(content);
				serviceResponse.getQuesInfo().setAuthorid(authorId);
				serviceResponse.getQuesInfo().setAnsnum(0);
				serviceResponse.getQuesInfo().setType(type);
				serviceResponse.getQuesInfo().setTitle(title);
				Date date = new Date();
			    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				serviceResponse.getQuesInfo().setTime(simpleDateFormat.format(date));
				int result = this.quesService.SaveQues(serviceResponse);
				if(result == 0) {
					quesResponse.setCode(0);
				}else {
					quesResponse.setCode(2);
				}
			}else {
				quesResponse.setCode(3);
			}
		}
		return quesResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-ID is Empty
	 * 2-Service Error*/
	@ResponseBody
	@RequestMapping(value = "/deleteques", method = RequestMethod.GET)
	public QuesResponse DeleteQues(@RequestParam long quesid) {
		QuesResponse quesResponse = new QuesResponse();
		if(quesid == 0) {
			quesResponse.setCode(1);
		}else {
			int result = this.quesService.DeleteQues(quesid);
			if(result == 0) {
				quesResponse.setCode(0);
			}else {
				quesResponse.setCode(2);
			}
		}
		return quesResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-QuesID is Empty
	 * 2-UserID is Empty
	 * 3-Service Error
	 * 4-Content is Empty*/
	@ResponseBody
	@RequestMapping(value = "/addans", method = RequestMethod.GET)
	public QuesResponse AddComment(@RequestParam long quesid,
						@RequestParam long userid,
						@RequestParam String username,
						@RequestParam String content) {
		QuesResponse quesResponse = new QuesResponse();
		if(quesid ==0) {
			quesResponse.setCode(1);
		} else if(userid == 0) {
			quesResponse.setCode(2);
		} else if(content == null || content.equals("")) {
			quesResponse.setCode(4);
		} else {
			Date date = new Date();
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			int result = this.quesService.AddAnswer(quesid, userid, username, simpleDateFormat.format(date), content);
			if(result == 0) {
				quesResponse.setCode(0);
			}else {
				quesResponse.setCode(3);
			}
		}
		return quesResponse;
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
			List<QuesInfo> result = this.quesService.FindByTitle(title, page);
			if(result == null) {
				searchResponse.setCode(2);
			}else {
				int size = this.quesService.GetSizeOfSearch(title);
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
	 * 1-Params is Empty
	 * 2-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/addhotquestech", method = RequestMethod.GET)
	public HotQuesResponse AddHotQuesTech(@RequestParam long quesid, @RequestParam String title,
										@RequestParam String headline, @RequestParam int joinnum, @RequestParam String time) {
		HotQuesResponse hotQuesResponse = new HotQuesResponse();
		if(title == null || title.equals("") || quesid == 0 || headline == null || headline.equals("")
				|| time == null || time.equals("")) {
			hotQuesResponse.setCode(1);
		}else {
			int result = this.hotQuesService.SaveHotTechQues(quesid, title, headline, joinnum, time);
			if(result == 0) {
				hotQuesResponse.setCode(0);
			}else {
				hotQuesResponse.setCode(2);
			}
		}
		return hotQuesResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Params is Empty
	 * 2-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/deletehotquestech", method = RequestMethod.GET)
	public HotQuesResponse DeleteHotQuesTech(@RequestParam long quesid) {
		HotQuesResponse hotQuesResponse = new HotQuesResponse();
		if(quesid == 0) {
			hotQuesResponse.setCode(1);
		}else {
			int result = this.hotQuesService.DeleteHotTechQues(quesid);
			if(result == 0) {
				hotQuesResponse.setCode(0);
			}else {
				hotQuesResponse.setCode(2);
			}
		}
		return hotQuesResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Params is Empty
	 * 2-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/addhotquestechjoinnum", method = RequestMethod.GET)
	public HotQuesResponse AddHotQuesTechJoinNum(@RequestParam long quesid) {
		HotQuesResponse hotQuesResponse = new HotQuesResponse();
		if(quesid == 0) {
			hotQuesResponse.setCode(1);
		}else {
			int result = this.hotQuesService.AddHotTechQuesJoin(quesid);
			if(result == 0) {
				hotQuesResponse.setCode(0);
			}else {
				hotQuesResponse.setCode(2);
			}
		}
		return hotQuesResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/gethotquestech", method = RequestMethod.GET)
	public HotQuesResponse GetHotQuesTech() {
		HotQuesResponse hotQuesResponse = new HotQuesResponse();
		List<?> result = this.hotQuesService.GetHotTechQues();
		if(result == null) {
			hotQuesResponse.setCode(1);
		}else {
			hotQuesResponse.setCode(0);
			hotQuesResponse.setItems(result);
		}
		return hotQuesResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Params is Empty
	 * 2-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/addhotqueslife", method = RequestMethod.GET)
	public HotQuesResponse AddHotQuesLife(@RequestParam long quesid, @RequestParam String title,
										@RequestParam String headline, @RequestParam int joinnum, @RequestParam String time) {
		HotQuesResponse hotQuesResponse = new HotQuesResponse();
		if(title == null || title.equals("") || quesid == 0 || headline == null || headline.equals("")
				|| time == null || time.equals("")) {
			hotQuesResponse.setCode(1);
		}else {
			int result = this.hotQuesService.SaveHotLifeQues(quesid, title, headline, joinnum, time);
			if(result == 0) {
				hotQuesResponse.setCode(0);
			}else {
				hotQuesResponse.setCode(2);
			}
		}
		return hotQuesResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Params is Empty
	 * 2-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/deletehotqueslife", method = RequestMethod.GET)
	public HotQuesResponse DeleteHotQuesLife(@RequestParam long quesid) {
		HotQuesResponse hotQuesResponse = new HotQuesResponse();
		if(quesid == 0) {
			hotQuesResponse.setCode(1);
		}else {
			int result = this.hotQuesService.DeleteHotLifeQues(quesid);
			if(result == 0) {
				hotQuesResponse.setCode(0);
			}else {
				hotQuesResponse.setCode(2);
			}
		}
		return hotQuesResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Params is Empty
	 * 2-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/addhotqueslifejoinnum", method = RequestMethod.GET)
	public HotQuesResponse AddHotQuesLifeJoinNum(@RequestParam long quesid) {
		HotQuesResponse hotQuesResponse = new HotQuesResponse();
		if(quesid == 0) {
			hotQuesResponse.setCode(1);
		}else {
			int result = this.hotQuesService.AddHotLifeQuesJoin(quesid);
			if(result == 0) {
				hotQuesResponse.setCode(0);
			}else {
				hotQuesResponse.setCode(2);
			}
		}
		return hotQuesResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Service Or Index Error*/
	@ResponseBody
	@RequestMapping(value = "/gethotqueslife", method = RequestMethod.GET)
	public HotQuesResponse GetHotQuesLife() {
		HotQuesResponse hotQuesResponse = new HotQuesResponse();
		List<?> result = this.hotQuesService.GetHotLifeQues();
		if(result == null) {
			hotQuesResponse.setCode(1);
		}else {
			hotQuesResponse.setCode(0);
			hotQuesResponse.setItems(result);
		}
		return hotQuesResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/getquesstar", method = RequestMethod.GET)
	public QuesStarResponse GetQuesStars() {
		QuesStarResponse quesStarResponse = new QuesStarResponse(); 
		List<?> result = this.quesStarService.GetQuesStar();
		if(result == null) {
			quesStarResponse.setCode(1);
		}else {
			quesStarResponse.setCode(0);
			quesStarResponse.setItems(result);
		}
		return quesStarResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/addquesstar", method = RequestMethod.GET)
	public QuesStarResponse AddQuesStars(@RequestParam long userid, @RequestParam String name, @RequestParam String des) {
		QuesStarResponse quesStarResponse = new QuesStarResponse(); 
		int result = this.quesStarService.AddQuesStar(userid, des, name);
		if(result != 0) {
			quesStarResponse.setCode(1);
		}else {
			quesStarResponse.setCode(0);
		}
		return quesStarResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/deletequesstar", method = RequestMethod.GET)
	public QuesStarResponse DeleteQuesStars(@RequestParam long userid) {
		QuesStarResponse quesStarResponse = new QuesStarResponse(); 
		int result = this.quesStarService.DeleteQuesStar(userid);
		if(result != 0) {
			quesStarResponse.setCode(1);
		}else {
			quesStarResponse.setCode(0);
		}
		return quesStarResponse;
	}
	
	/**
	 * Code:
	 * 0-正确
	 * 1-Error*/
	@ResponseBody
	@RequestMapping(value = "/getbyauthor", method = RequestMethod.GET)
	public SearchResponse GetByAuthor(@RequestParam long authorid) {
		SearchResponse response = new SearchResponse(); 
		List<?> result = this.quesService.GetQuesByAuthor(authorid);
		response.setCode(0);
		response.setResult(result);
		return response;
	}
}
