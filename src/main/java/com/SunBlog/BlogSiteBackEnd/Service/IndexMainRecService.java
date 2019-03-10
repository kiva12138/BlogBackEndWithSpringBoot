package com.SunBlog.BlogSiteBackEnd.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.SunBlog.BlogSiteBackEnd.Entity.IndexMainRecInfo;
import com.SunBlog.BlogSiteBackEnd.Repository.IndexMainRecRepository;

@Service
public class IndexMainRecService {
	@Autowired
	private IndexMainRecRepository indexMainRecRepository;

	public IndexMainRecRepository getIndexMainRecRepository() {
		return indexMainRecRepository;
	}

	public void setIndexMainRecRepository(IndexMainRecRepository indexMainRecRepository) {
		this.indexMainRecRepository = indexMainRecRepository;
	}

	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int SaveIndexMainRec(long blogid, String title, String headline, String time, int read) {
		if(blogid == 0 || title == null || title.equals("")
				|| headline == null || headline.equals("")
				|| time == null || time.equals("")
				|| read<0) {
			System.out.println("Cannot Save a New IndexMainRecService");
			return 1;
		}else {
			IndexMainRecInfo infoToSave = new IndexMainRecInfo();
			infoToSave.setBlogid(blogid);
			infoToSave.setHeadline(headline);
			infoToSave.setReadnum(read);
			infoToSave.setTime(time);
			infoToSave.setTitle(title);
			IndexMainRecInfo result = this.indexMainRecRepository.saveAndFlush(infoToSave);
			if(result == null) {
				System.out.println("Cannot Save IndexRecInfo to DB");
				return 1;
			}
			return 0;
		}
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int DeleteIndexMainRec(long recid) {
		if(recid == 0) {
			System.out.println("Cannot Delete an empty info");
			return 1;
		}else {
			this.indexMainRecRepository.deleteByRecid(recid);
			return 0;
		}
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int AddReadNum(long recid) {
		if(recid == 0) {
			System.out.println("Cannot Add Read Num to an empty info");
			return 1;
		}else {
			this.indexMainRecRepository.addReadNum(1, recid);
			return 0;
		}
	}
	
	/**
	 * Return:
	 * Not Null -正确
	 * Null - 错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public List<IndexMainRecInfo> GetIndexRecs() {
		List<IndexMainRecInfo> indexMainRecInfos = this.indexMainRecRepository.findAll();
		return indexMainRecInfos;
	}
}
