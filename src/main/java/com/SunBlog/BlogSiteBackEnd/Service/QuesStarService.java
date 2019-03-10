package com.SunBlog.BlogSiteBackEnd.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.SunBlog.BlogSiteBackEnd.Entity.QuesStarInfo;
import com.SunBlog.BlogSiteBackEnd.Repository.QuesStarRepository;

@Service
public class QuesStarService {
	@Autowired
	private QuesStarRepository quesStarRepository;
	
	public QuesStarRepository getQuesStarRepository() {
		return quesStarRepository;
	}

	public void setQuesStarRepository(QuesStarRepository quesStarRepository) {
		this.quesStarRepository = quesStarRepository;
	}

	/**
	 * Return:
	 * 0 -正确
	 * 1 -File Error*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int AddQuesStar(long userid, String des, String name) {
		if(userid == 0||des==null || des.equals("") || name==null || name.equals("")) {
			System.out.println("The Info is Empty");
			return 1;
		}else {
			QuesStarInfo quesStarInfo = new QuesStarInfo();
			quesStarInfo.setName(name);
			quesStarInfo.setDes(des);
			quesStarInfo.setUserid(userid);
			this.quesStarRepository.save(quesStarInfo);
			return 0;
		}
	}
	
	/**
	 * Return:
	 * 0 -正确
	 * 1 -File Error*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int DeleteQuesStar(long userid) {
		if(userid == 0) {
			System.out.println("The Info is Empty");
			return 1;
		}else {
			this.quesStarRepository.deleteByUserid(userid);
			return 0;
		}
	}
	
	/**
	 * Return:
	 * 0 -正确
	 * 1 -File Error*/
	@Transactional(propagation = Propagation.REQUIRED)
	public List<?> GetQuesStar() {
		List<QuesStarInfo> quesStarInfos = this.quesStarRepository.findAll();
		if(quesStarInfos == null) {
			System.out.println("The Info is Empty");
		}
		return quesStarInfos;
	}
}
