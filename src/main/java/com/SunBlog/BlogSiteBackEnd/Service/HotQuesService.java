package com.SunBlog.BlogSiteBackEnd.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.SunBlog.BlogSiteBackEnd.Entity.HotQuesLifeInfo;
import com.SunBlog.BlogSiteBackEnd.Entity.HotQuesTechInfo;
import com.SunBlog.BlogSiteBackEnd.Repository.HotQuesLifeRepository;
import com.SunBlog.BlogSiteBackEnd.Repository.HotQuesTechRepository;

@Service
public class HotQuesService {
	@Autowired
	private HotQuesLifeRepository hotQuesLifeRepository;
	@Autowired
	private HotQuesTechRepository hotQuesTechRepository;
	public HotQuesLifeRepository getHotQuesLifeRepository() {
		return hotQuesLifeRepository;
	}
	public void setHotQuesLifeRepository(HotQuesLifeRepository hotQuesLifeRepository) {
		this.hotQuesLifeRepository = hotQuesLifeRepository;
	}
	public HotQuesTechRepository getHotQuesTechRepository() {
		return hotQuesTechRepository;
	}
	public void setHotQuesTechRepository(HotQuesTechRepository hotQuesTechRepository) {
		this.hotQuesTechRepository = hotQuesTechRepository;
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int SaveHotTechQues(long quesid, String title, String headline, int joinnum, String time) {
		if(quesid == 0 || title == null || title.equals("")
				|| headline == null || headline.equals("")
				|| time == null || time.equals("")) {
			return 1;
		}else {		
			HotQuesTechInfo hotQuesTechInfo = new HotQuesTechInfo();
			hotQuesTechInfo.setTitle(title);
			hotQuesTechInfo.setHeadline(headline);
			hotQuesTechInfo.setJoinnum(joinnum);
			hotQuesTechInfo.setTime(time);
			hotQuesTechInfo.setQuesid(quesid);
			HotQuesTechInfo result = this.hotQuesTechRepository.saveAndFlush(hotQuesTechInfo);
			if(result == null) {
				return 1;
			}else {
				return 0;
			}
		}
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int DeleteHotTechQues(long quesid) {
		if(quesid == 0) {
			return 1;
		}
		this.hotQuesTechRepository.deleteByQuesid(quesid);
		return 0;
	}

	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int AddHotTechQuesJoin(long quesid) {
		if(quesid == 0) {
			return 1;
		}
		this.hotQuesTechRepository.addJoinNum(quesid);
		return 0;
	}
	
	/**
	 * Return:
	 * Not Null-正确
	 * Null    -错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public List<?> GetHotTechQues() {
		List<?> result = this.hotQuesTechRepository.findAll();
		return result;
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int SaveHotLifeQues(long quesid, String title, String headline, int joinnum, String time) {
		if(quesid == 0 || title == null || title.equals("")
				|| headline == null || headline.equals("")
				|| time == null || time.equals("")) {
			return 1;
		}else {		
			HotQuesLifeInfo hotQuesLifeInfo = new HotQuesLifeInfo();
			hotQuesLifeInfo.setTitle(title);
			hotQuesLifeInfo.setHeadline(headline);
			hotQuesLifeInfo.setJoinnum(joinnum);
			hotQuesLifeInfo.setTime(time);
			hotQuesLifeInfo.setQuesid(quesid);
			HotQuesLifeInfo result = this.hotQuesLifeRepository.saveAndFlush(hotQuesLifeInfo);
			if(result == null) {
				return 1;
			}else {
				return 0;
			}
		}
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int DeleteHotLifeQues(long quesid) {
		if(quesid == 0) {
			return 1;
		}
		this.hotQuesLifeRepository.deleteByQuesid(quesid);
		return 0;
	}

	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int AddHotLifeQuesJoin(long quesid) {
		if(quesid == 0) {
			return 1;
		}
		this.hotQuesLifeRepository.addJoinNum(quesid);
		return 0;
	}
	
	/**
	 * Return:
	 * Not Null-正确
	 * Null    -错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public List<?> GetHotLifeQues() {
		List<?> result = this.hotQuesLifeRepository.findAll();
		return result;
	}
}
