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
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.SunBlog.BlogSiteBackEnd.Entity.ResInfo;
import com.SunBlog.BlogSiteBackEnd.Reponse.ResResponse;
import com.SunBlog.BlogSiteBackEnd.Repository.ResRepository;
import com.SunBlog.BlogSiteBackEnd.Repository.UserRepository;

@Service
public class ResService {
	@Autowired
	private ResRepository resRepository;
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Return:
	 * 0:Success
	 * 1:Resinfo Or File Empty 
	 * 2:File Error*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int SaveRes(ResResponse resResponse, MultipartFile file) {
		if(resResponse.getResInfo() == null || file.isEmpty()) {
			System.out.println("Cannot save an empty resource!");
			return 1;
		}else {
			ResInfo resInfo = this.resRepository.saveAndFlush(resResponse.getResInfo());
			resResponse.getResInfo().setResid(resInfo.getResid());
			try {
				File dir = new File(ResourceUtils.getURL("classpath:").getPath()+"Res/"+resResponse.getResInfo().getResid());
				if(dir.exists()) {
					System.out.println("Resource File Already Exists! Trying to delete...");
					DeleteRes(resResponse.getResInfo().getResid());
				}
				dir.mkdirs();
				File res = new File(ResourceUtils.getURL("classpath:").getPath()+"Res/"+resResponse.getResInfo().getResid()+"/res");
				res.createNewFile();
				PrintStream pStream = new PrintStream(res);
				pStream.println(resResponse.getDescription());
				pStream.close();
				File comments = new File(ResourceUtils.getURL("classpath:").getPath()+"Res/"+resResponse.getResInfo().getResid()+"/comments");
				comments.createNewFile();
				String fileName = file.getOriginalFilename();
		        String filePath = ResourceUtils.getURL("classpath:").getPath()+"Res/"+resInfo.getResid()+"/";
		        File dest = new File(filePath + fileName);
		        file.transferTo(dest);
		        this.userRepository.addRes(resResponse.getResInfo().getAuthorid());
			}catch(Exception e) {
				System.out.println("Cannot Write Resource to Files");
				return 2;
			}
			return 0;
		}
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int DeleteRes(long resid) {
		if(resid == 0) {
			System.out.println("Cannot Delete an Empty Resinfo");
			return 1;
		}else {
			String filename = this.resRepository.findByResid((int) resid).getFilename();
			this.resRepository.deleteByResid(resid);
			try {
				File file = new File(ResourceUtils.getURL("classpath:").getPath()+"Res/"+resid+"/res");
				if(!file.delete()) {
					return 1;
				}
				file = new File(ResourceUtils.getURL("classpath:").getPath()+"Res/"+resid+"/comments");
				if(!file.delete()) {
					return 1;
				}
				file = new File(ResourceUtils.getURL("classpath:").getPath()+"Res/"+resid+"/"+filename);
				if(!file.delete()) {
					return 1;
				}
				file = new File(ResourceUtils.getURL("classpath:").getPath()+"Res/"+resid);
				if(!file.delete()) {
					return 1;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("Cannot delete resource and comments");
				return 1;
			}
			return 0;
		}
	}
	
	/**
	 * 评论顺序
	 * ID Name Time Content*/
	@Transactional(propagation = Propagation.REQUIRED)
	public ResResponse FindResById(long resid) {
		if(resid == 0) {
			System.out.println("Cannot Find an Empty Resinfo");
			return null;
		}else {
			ResResponse resResponse = new ResResponse();
			ResInfo resInfo = this.resRepository.findByResid((int) resid);
			resResponse.setResInfo(resInfo);
			try {
				FileInputStream resInputStream = new FileInputStream(ResourceUtils.getURL("classpath:").getPath()+"Res/"+resid+"/res");
				BufferedReader resBufferedReader = new BufferedReader(new InputStreamReader(resInputStream));
				String temp = resBufferedReader.readLine();
				if(temp!=null) {
					resResponse.setDescription(temp);
				}
				if(resInfo.getCommentsnum() == 0) {
					resResponse.setResComments(null);
				}else {
					resInputStream = new FileInputStream(ResourceUtils.getURL("classpath:").getPath()+"Res/"+resid+"/comments");
					resBufferedReader = new BufferedReader(new InputStreamReader(resInputStream));
					ResResponse.ResComment[] comments = new ResResponse.ResComment[resInfo.getCommentsnum()];
					for(int i=0; i<resInfo.getCommentsnum(); i++) {
						comments[i] = resResponse.new ResComment();
						temp = resBufferedReader.readLine();
						comments[i].setUserid(Integer.valueOf(temp));
						temp = resBufferedReader.readLine();
						comments[i].setName(temp);
						temp = resBufferedReader.readLine();
						comments[i].setTime(temp);
						temp = resBufferedReader.readLine();
						comments[i].setContent(temp);
					}
					resResponse.setResComments(comments);;
				}
				resBufferedReader.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Cannot Open Resource Files");
				return null;
			}
			return resResponse;
		}
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int AddComment(long resid, long userid, String username, String time, String content) {
		this.resRepository.addCommentNum(1, resid);
		FileWriter writer;
		try {
			writer = new FileWriter(ResourceUtils.getURL("classpath:").getPath()+"Res/"+resid+"/comments", true);
			writer.write(String.valueOf(userid)+'\n'); 
			writer.write(username+"\n");
			writer.write(time+'\n');
			writer.write(content+'\n'); 
	        writer.close();  
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cannot Add Comment");
			return 1;
		}
		return 0;
	}
	
	/**
	 * Return:
	 * NULL-Error
	 * Else-Success*/
	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseEntity<InputStreamResource> Download(long resid) {
		if(resid ==0) {
			System.out.println("Cannot Add Download Num to an Empty Resource");
			return null;
		}else {
			String filename=this.resRepository.findByResid(resid).getFilename();
			if(filename == null || filename.equals("")) {
				return null;
			}
			try {
				FileSystemResource fileSystemResource = new FileSystemResource(ResourceUtils.getURL("classpath:").getPath()+"Res/"+resid+"/"+filename);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileSystemResource.getFilename()));
		        headers.add("Pragma", "no-cache");
		        headers.add("Expires", "0");
				this.resRepository.addDownNum(1, resid);
				return  ResponseEntity
		                .ok()
		                .headers(headers)
		                .contentLength(fileSystemResource.contentLength())
		                .contentType(MediaType.parseMediaType("application/octet-stream"))
		                .body(new InputStreamResource(fileSystemResource.getInputStream()));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Cannot Download Files");
				return null;
			}
		}
	}
	
	/**
	 * Return:
	 * Not Null-正确
	 * Null-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ResInfo> FindByTitle(String title, int page) {
		if(title == null || title.equals("")) {
			return null;
		}else {
			List<ResInfo> result = resRepository.findByTitleLike(title);
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
			return resRepository.findByTitleLike(title).size();			
		}
	}
	
	/**
	 * Return:
	 * Not NULL -正确
	 * NULL -File Error*/
	@Transactional(propagation = Propagation.REQUIRED)
	public List<?> GetResByAuthor(long uid) {
		List<?> result = this.resRepository.findByAuthor(uid);
		return result;
	}

	public ResRepository getRepository() {
		return resRepository;
	}

	public void setRepository(ResRepository repository) {
		this.resRepository = repository;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
