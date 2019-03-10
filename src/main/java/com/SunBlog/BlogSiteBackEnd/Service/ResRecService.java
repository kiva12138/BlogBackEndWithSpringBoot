package com.SunBlog.BlogSiteBackEnd.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.SunBlog.BlogSiteBackEnd.Entity.ResHotInfo;
import com.SunBlog.BlogSiteBackEnd.Entity.ResNewInfo;
import com.SunBlog.BlogSiteBackEnd.Repository.ResHotRepository;
import com.SunBlog.BlogSiteBackEnd.Repository.ResNewRepository;

@Service
public class ResRecService {
	@Autowired
	private ResHotRepository resHotRepository;
	@Autowired
	private ResNewRepository resNewRepository;
	public ResHotRepository getResHotRepository() {
		return resHotRepository;
	}
	public void setResHotRepository(ResHotRepository resHotRepository) {
		this.resHotRepository = resHotRepository;
	}
	public ResNewRepository getResNewRepository() {
		return resNewRepository;
	}
	public void setResNewRepository(ResNewRepository resNewRepository) {
		this.resNewRepository = resNewRepository;
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int SaveResHot(long resid, String title, String format, int downnum, String time) {
		if(resid == 0 || title == null || title.equals("")
				|| format == null || format.equals("")
				|| time == null || time.equals("")) {
			return 1;
		}else {		
			ResHotInfo resHotInfo = new ResHotInfo();
			resHotInfo.setTitle(title);
			resHotInfo.setFormat(format);
			resHotInfo.setDownnum(downnum);
			resHotInfo.setTime(time);
			resHotInfo.setResid(resid);
			ResHotInfo result = this.resHotRepository.saveAndFlush(resHotInfo);
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
	public int DeleteResHot(long resid) {
		if(resid == 0) {
			return 1;
		}
		this.resHotRepository.deleteByResid(resid);
		return 0;
	}

	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int AddResHotDownNum(long resid) {
		if(resid == 0) {
			return 1;
		}
		this.resHotRepository.addDownNum(resid);
		return 0;
	}
	
	/**
	 * Return:
	 * Not Null-正确
	 * Null    -错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public List<?> GetResHot() {
		List<?> result = this.resHotRepository.findAll();
		return result;
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int SaveResNew(long resid, String title, String format, int downnum, String time) {
		if(resid == 0 || title == null || title.equals("")
				|| format == null || format.equals("")
				|| time == null || time.equals("")) {
			return 1;
		}else {		
			ResNewInfo resNewInfo = new ResNewInfo();
			resNewInfo.setTitle(title);
			resNewInfo.setFormat(format);
			resNewInfo.setDownnum(downnum);
			resNewInfo.setTime(time);
			resNewInfo.setResid(resid);
			ResNewInfo result = this.resNewRepository.saveAndFlush(resNewInfo);
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
	public int DeleteResNew(long resid) {
		if(resid == 0) {
			return 1;
		}
		this.resNewRepository.deleteByResid(resid);
		return 0;
	}

	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int AddResNewDownNum(long resid) {
		if(resid == 0) {
			return 1;
		}
		this.resNewRepository.addDownNum(resid);
		return 0;
	}
	
	/**
	 * Return:
	 * Not Null-正确
	 * Null    -错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public List<?> GetResNew() {
		List<?> result = this.resNewRepository.findAll();
		return result;
	}
}
