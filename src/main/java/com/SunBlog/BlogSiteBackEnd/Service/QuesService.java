package com.SunBlog.BlogSiteBackEnd.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.SunBlog.BlogSiteBackEnd.Entity.QuesInfo;
import com.SunBlog.BlogSiteBackEnd.Reponse.QuesResponse;
import com.SunBlog.BlogSiteBackEnd.Repository.QuesRepository;
import com.SunBlog.BlogSiteBackEnd.Repository.UserRepository;

@Service
public class QuesService {
	@Autowired
	private QuesRepository quesRepository;
	@Autowired
	private UserRepository userRepository;

	public QuesRepository getQuesRepository() {
		return quesRepository;
	}

	public void setQuesRepository(QuesRepository quesRepository) {
		this.quesRepository = quesRepository;
	}

	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int SaveQues(QuesResponse quesResponse) {
		if(quesResponse.getQuesInfo() == null) {
			System.out.println("Cannot save an empty question!");
			return 1;
		}else {
			QuesInfo quesInfo = this.quesRepository.saveAndFlush(quesResponse.getQuesInfo());
			quesResponse.getQuesInfo().setQuesid(quesInfo.getQuesid());
			try {
				File dir = new File(ResourceUtils.getURL("classpath:").getPath()+"Ques/"+quesResponse.getQuesInfo().getQuesid());
				if(dir.exists()) {
					System.out.println("Question File Already Exists! Trying to delete...");
					DeleteQues(quesResponse.getQuesInfo().getQuesid());
				}
				dir.mkdirs();
				File ques = new File(ResourceUtils.getURL("classpath:").getPath()+"Ques/"+quesResponse.getQuesInfo().getQuesid()+"/ques");
				ques.createNewFile();
				PrintStream pStream = new PrintStream(ques);
				pStream.println(quesResponse.getContent());
				pStream.close();
				File ans = new File(ResourceUtils.getURL("classpath:").getPath()+"Ques/"+quesResponse.getQuesInfo().getQuesid()+"/ans");
				ans.createNewFile();
				this.userRepository.addQues(quesResponse.getQuesInfo().getAuthorid());
			}catch(Exception e) {
				System.out.println("Cannot Write Question to Files");
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
	public int DeleteQues(long quesid) {
		if(quesid == 0) {
			System.out.println("Cannot Delete an Empty Quesinfo");
			return 1;
		}else {
			this.quesRepository.deleteByQuesid(quesid);
			try {
				File file = new File(ResourceUtils.getURL("classpath:").getPath()+"Ques/"+quesid+"/ques");
				if(!file.delete()) {
					return 1;
				}
				file = new File(ResourceUtils.getURL("classpath:").getPath()+"Ques/"+quesid+"/ans");
				if(!file.delete()) {
					return 1;
				}
				file = new File(ResourceUtils.getURL("classpath:").getPath()+"Ques/"+quesid);
				if(!file.delete()) {
					return 1;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("Cannot delete question and answers");
				return 1;
			}
			return 0;
		}
	}
	
	/**
	 * 评论顺序
	 * ID Name Time Content Sequence*/
	@Transactional(propagation = Propagation.REQUIRED)
	public QuesResponse FindQuesById(long quesid) {
		if(quesid == 0) {
			System.out.println("Cannot Find an Empty Quesinfo");
			return null;
		}else {
			QuesResponse quesResponse = new QuesResponse();
			QuesInfo quesInfo = this.quesRepository.findByQuesid((int) quesid);
			quesResponse.setQuesInfo(quesInfo);
			try {
				FileInputStream quesInputStream = new FileInputStream(ResourceUtils.getURL("classpath:").getPath()+"Ques/"+quesid+"/ques");
				BufferedReader quesBufferedReader = new BufferedReader(new InputStreamReader(quesInputStream));
				String temp = quesBufferedReader.readLine();
				if(temp!=null) {
					quesResponse.setContent(temp);
				}
				if(quesInfo.getAnsnum() == 0) {
					quesResponse.setQuesAnswers(null);
				}else {
					quesInputStream = new FileInputStream(ResourceUtils.getURL("classpath:").getPath()+"Ques/"+quesid+"/ans");
					quesBufferedReader = new BufferedReader(new InputStreamReader(quesInputStream));
					QuesResponse.QuesAnswer[] answers = new QuesResponse.QuesAnswer[quesInfo.getAnsnum()];
					for(int i=0; i<quesInfo.getAnsnum(); i++) {
						answers[i] = quesResponse.new QuesAnswer();
						temp = quesBufferedReader.readLine();
						answers[i].setUserid(Integer.valueOf(temp));
						temp = quesBufferedReader.readLine();
						answers[i].setUsername(temp);
						temp = quesBufferedReader.readLine();
						answers[i].setTime(temp);
						temp = quesBufferedReader.readLine();
						answers[i].setAnsContent(temp);
						temp = quesBufferedReader.readLine();
						answers[i].setSeq(Integer.valueOf(temp));
					}
					quesResponse.setQuesAnswers(answers);
				}
				quesBufferedReader.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Cannot Open Question Files");
				return null;
			}
			return quesResponse;
		}
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int AddAnswer(long quesid, long userid, String username, String time, String content) {
		this.quesRepository.addAnsNum(1, quesid);
		QuesInfo quesInfo = this.quesRepository.findByQuesid(quesid);
		int seq = quesInfo.getAnsnum()+1;
		FileWriter writer;
		try {
			writer = new FileWriter(ResourceUtils.getURL("classpath:").getPath()+"Ques/"+quesid+"/ans", true);
			writer.write(String.valueOf(userid)+'\n'); 
			writer.write(username+"\n");
			writer.write(time+'\n');
			writer.write(content+'\n'); 
			writer.write(seq+"\n");
	        writer.close();  
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cannot Add Answer");
			return 1;
		}
		return 0;
	}
	
	/**
	 * Return:
	 * Not Null-正确
	 * Null-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public List<QuesInfo> FindByTitle(String title, int page) {
		if(title == null || title.equals("")) {
			return null;
		}else {
			List<QuesInfo> result = quesRepository.findByTitleLike(title);
			int fromIndex = 0;
			int toIndex = 0;
			int maxIndex = result.size()-1;
			if(maxIndex < (page-1)*10) {
				return null;
			}else {
				fromIndex = (page-1)*10;
				if(page*10-1 < maxIndex) {
					toIndex = page*10;
				}else {
					toIndex = maxIndex+1;
				}
			}
			result = result.subList(fromIndex, toIndex);
			return result;
		}
	}
	
	/**
	 * Return:
	 *  >= 0 -正确
	 *   -1  -错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int GetSizeOfSearch(String title) {
		if(title == null || title.equals("")) {
			return -1;
		}else {
			return quesRepository.findByTitleLike(title).size();			
		}
	}
	
	/**
	 * Return:
	 * Not NULL -正确
	 * NULL -File Error*/
	@Transactional(propagation = Propagation.REQUIRED)
	public List<?> GetQuesByAuthor(long uid) {
		List<?> result = this.quesRepository.findByAuthor(uid);
		return result;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
