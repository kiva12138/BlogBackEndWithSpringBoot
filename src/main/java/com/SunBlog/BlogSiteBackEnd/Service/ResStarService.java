package com.SunBlog.BlogSiteBackEnd.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.SunBlog.BlogSiteBackEnd.Entity.ResStarInfo;
import com.SunBlog.BlogSiteBackEnd.Repository.ResStarRepository;

@Service
public class ResStarService {
	@Autowired
	private ResStarRepository resStarRepository;

	public ResStarRepository getResStarRepository() {
		return resStarRepository;
	}

	public void setResStarRepository(ResStarRepository resStarRepository) {
		this.resStarRepository = resStarRepository;
	}
	/**
	 * Return:
	 * 0 -正确
	 * 1 -File Error*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int AddResStar(long userid, String name, int resnum) {
		if(userid == 0||resnum<0 || name==null || name.equals("")) {
			System.out.println("The Info is Empty");
			return 1;
		}else {
			ResStarInfo resStarInfo = new ResStarInfo();
			resStarInfo.setName(name);
			resStarInfo.setResnum(resnum);
			resStarInfo.setUserid(userid);
			this.resStarRepository.save(resStarInfo);
			return 0;
		}
	}
	
	/**
	 * Return:
	 * 0 -正确
	 * 1 -File Error*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int DeleteResStar(long userid) {
		if(userid == 0) {
			System.out.println("The Info is Empty");
			return 1;
		}else {
			this.resStarRepository.deleteByUserid(userid);
			return 0;
		}
	}
	
	/**
	 * Return:
	 * NOT NULL -正确
	 * NULL     -Error*/
	@Transactional(propagation = Propagation.REQUIRED)
	public List<?> GetResStar() {
		List<ResStarInfo> resStarInfos = this.resStarRepository.findAll();
		if(resStarInfos == null) {
			System.out.println("The Info is Empty");
		}
		return resStarInfos;
	}
	
	/**
	 * Return:
	 * 0 -正确
	 * 1 -File Error*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int AddResStarResNum(long userid) {
		if(userid <= 0) {
			System.out.println("The Info is Empty");
			return 1;
		}
		this.resStarRepository.addResNum(userid);
		return 0;
	}

}
