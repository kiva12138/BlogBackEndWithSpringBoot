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
import org.springframework.web.multipart.MultipartFile;

import com.SunBlog.BlogSiteBackEnd.Entity.BlogInfo;
import com.SunBlog.BlogSiteBackEnd.Reponse.BlogResponse;
import com.SunBlog.BlogSiteBackEnd.Reponse.PicRecommandResponse;
import com.SunBlog.BlogSiteBackEnd.Repository.BlogRepository;
import com.SunBlog.BlogSiteBackEnd.Repository.UserRepository;

@Service
public class BlogService {
	private BlogRepository blogRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public BlogService(BlogRepository blogRepository) {
		this.setBlogRepository(blogRepository);
	}

	public BlogRepository getBlogRepository() {
		return blogRepository;
	}

	public void setBlogRepository(BlogRepository blogRepository) {
		this.blogRepository = blogRepository;
	}

	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int SaveBlog(BlogResponse blogResponse) {
		if(blogResponse.getBlogInfo() == null) {
			System.out.println("Cannot Save a New Bloginfo");
			return 1;
		}else {
			BlogInfo blogInfo = this.blogRepository.saveAndFlush(blogResponse.getBlogInfo());
			blogResponse.getBlogInfo().setBlogid(blogInfo.getBlogid());
			try {
				File dir = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/"+blogResponse.getBlogInfo().getBlogid());
				if(dir.exists()) {
					System.out.println("Blog File Already Exists! Trying to delete...");
					DeleteBlog(blogResponse.getBlogInfo().getBlogid());
				}
				dir.mkdirs();
				File blog = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/"+blogResponse.getBlogInfo().getBlogid()+"/blog");
				blog.createNewFile();
				PrintStream pStream = new PrintStream(blog);
				pStream.println(blogResponse.getContent());
				pStream.close();
				File comments = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/"+blogResponse.getBlogInfo().getBlogid()+"/comments");
				comments.createNewFile();
				this.userRepository.addBlog(blogResponse.getBlogInfo().getAuthorid());
			}catch (Exception e) {
				System.out.println("Fail To Save Blog And Comments");
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
	public int DeleteBlog(long blogid) {
		if(blogid == 0) {
			System.out.println("Cannot Delete an Empty Bloginfo");
			return 1;
		}else {
			this.blogRepository.deleteByBlogid(blogid);
			try {
				File file = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/"+blogid+"/blog");
				if(!file.delete()) {
					return 1;
				}
				file = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/"+blogid+"/comments");
				if(!file.delete()) {
					return 1;
				}
				file = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/"+blogid);
				if(!file.delete()) {
					return 1;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("Cannot delete blog and comments");
				return 1;
			}
			return 0;
		}
	}
	
	/**
	 * 评论顺序
	 * ID Time Content*/
	@Transactional(propagation = Propagation.REQUIRED)
	public BlogResponse FindBlogById(long blogid) {
		if(blogid == 0) {
			System.out.println("Cannot Find an Empty Bloginfo");
			return null;
		}else {
			BlogResponse blogResponse = new BlogResponse();
			BlogInfo blogInfo = this.blogRepository.findByBlogid((int) blogid);
			blogResponse.setBlogInfo(blogInfo);
			try {
				FileInputStream blogInputStream = new FileInputStream(ResourceUtils.getURL("classpath:").getPath()+"Blogs/"+blogid+"/blog");
				BufferedReader blogBufferedReader = new BufferedReader(new InputStreamReader(blogInputStream));
				String temp = blogBufferedReader.readLine();
				if(temp!=null) {
					blogResponse.setContent(temp);
				}
				if(blogInfo.getCommentsnum() == 0) {
					blogResponse.setComments(null);
				}else {
					blogInputStream = new FileInputStream(ResourceUtils.getURL("classpath:").getPath()+"Blogs/"+blogid+"/comments");
					blogBufferedReader = new BufferedReader(new InputStreamReader(blogInputStream));
					BlogResponse.Comment[] comments = new BlogResponse.Comment[blogInfo.getCommentsnum()];
					for(int i=0; i<blogInfo.getCommentsnum(); i++) {
						comments[i] = blogResponse.new Comment();
						temp = blogBufferedReader.readLine();
						comments[i].setUserid(Integer.valueOf(temp));
						temp = blogBufferedReader.readLine();
						comments[i].setTime(temp);
						temp = blogBufferedReader.readLine();
						comments[i].setComentContent(temp);
					}
					blogResponse.setComments(comments);
				}
				blogBufferedReader.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Cannot Open Blog Files");
				return null;
			}
			return blogResponse;
		}
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int AddReadNum(long blogid) {
		this.blogRepository.addReadNum(1, blogid);
		return 0;
	}
	
	/**
	 * Return:
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int AddComments(long blogid, long userid, String time, String content) {
		this.blogRepository.addCommentsNum(1, blogid);
		 FileWriter writer;
		try {
			writer = new FileWriter(ResourceUtils.getURL("classpath:").getPath()+"Blogs/"+blogid+"/comments", true);
			writer.write(String.valueOf(userid)+'\n'); 
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
	 * 0-正确
	 * 1-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int ModifyBlog(long blogid, String title, String content) {
		try {
			BlogInfo blogInfo = this.blogRepository.findByBlogid((int) blogid);
			blogInfo.setTitle(title);
			this.blogRepository.save(blogInfo);
			File blog = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/"+blogid+"/blog");
			PrintStream pStream = new PrintStream(blog);
			pStream.println(content);
			pStream.close();
		} catch (IOException e) {
			System.out.println("Cannot Modify Blog");
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
	
	/**
	 * Return:
	 * Not Null-正确
	 * Null-错误*/
	@Transactional(propagation = Propagation.REQUIRED)
	public List<BlogInfo> FindByTitle(String title, int page) {
		if(title == null || title.equals("")) {
			return null;
		}else {
			List<BlogInfo> result = blogRepository.findByTitleLike(title);
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
			return blogRepository.findByTitleLike(title).size();			
		}
	}
	
	/**
	 * Return:
	 * 0 -正确
	 * 1 -BlogId Or File is Empty Or seq not Correct
	 * 2-File Error
	 * 
	 * 顺序：blogId title*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int AddPicRecommand(long blogId, String title, MultipartFile picFile, int seq) {
		if(title == null || title.equals("") || picFile == null || seq < 1 || seq > 4) {
			return 1;
		}else {
			try {
				File oldInfoFile;
				oldInfoFile = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/PicRecommand/"+seq);
				if(!oldInfoFile.exists()) {
					oldInfoFile.mkdirs();
				}
				oldInfoFile = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/PicRecommand/"+seq+"/info");
				if(oldInfoFile.exists()) {
					if(!oldInfoFile.delete()) {
						return 2;
					}
				}
				oldInfoFile.createNewFile();
				PrintStream pStream = new PrintStream(oldInfoFile);
				pStream.println(blogId);
				pStream.println(title);
				pStream.close();
				oldInfoFile = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/PicRecommand/"+seq+"/pic");
				if(oldInfoFile.exists()) {
					if(!oldInfoFile.delete()) {
						return 2;
					}
				}
				oldInfoFile.createNewFile();
				picFile.transferTo(oldInfoFile);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Deleting Old Files Error");
				return 2;
			}
			return 0;			
		}
	}
	
	/**
	 * Return:
	 * 0 -正确
	 * 1 -File Error*/
	@Transactional(propagation = Propagation.REQUIRED)
	public PicRecommandResponse.PicRecItems[] GetPicBlogRec() {
		PicRecommandResponse.PicRecItems[] items = new PicRecommandResponse.PicRecItems[4];
		PicRecommandResponse temp = new PicRecommandResponse();
		for(int i=1; i<=4; i++) {
			FileInputStream inputStream;
			try {
				items[i-1] = temp.new PicRecItems();
				inputStream = new FileInputStream(ResourceUtils.getURL("classpath:").getPath()+"Blogs/PicRecommand/"+i+"/info");
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				items[i-1].setBlogId(Integer.valueOf(bufferedReader.readLine()));
				items[i-1].setTitle(bufferedReader.readLine());
				/*ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/PicRecommand/"+i+"/pic")));
				ImageIO.write(bufferedImage, "png", outputStream);
				items[i-1].setImage(new String(Base64Coder.encode(outputStream.toByteArray())));*/
				items[i-1].setImage(ResourceUtils.getURL("classpath:").getPath()+"Blogs/PicRecommand/"+i+"/pic.png");
				bufferedReader.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error Occurs When handling files");
			}
			
		}
		return items;
	}
	
	/**
	 * Return:
	 * 0 -正确
	 * 1 -BlogId Or File is Empty Or seq not Correct
	 * 2-File Error
	 * 
	 * 顺序：blogId title*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int AddIndexPicRecommand(long blogId, String title, MultipartFile picFile, int seq) {
		if(title == null || title.equals("") || picFile == null || seq < 1 || seq > 4) {
			return 1;
		}else {
			try {
				File oldInfoFile;
				oldInfoFile = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/IndexPicRecommand/"+seq);
				if(!oldInfoFile.exists()) {
					oldInfoFile.mkdirs();
				}
				oldInfoFile = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/IndexPicRecommand/"+seq+"/info");
				if(oldInfoFile.exists()) {
					if(!oldInfoFile.delete()) {
						return 2;
					}
				}
				oldInfoFile.createNewFile();
				PrintStream pStream = new PrintStream(oldInfoFile);
				pStream.println(blogId);
				pStream.println(title);
				pStream.close();
				oldInfoFile = new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/IndexPicRecommand/"+seq+"/pic");
				if(oldInfoFile.exists()) {
					if(!oldInfoFile.delete()) {
						return 2;
					}
				}
				oldInfoFile.createNewFile();
				picFile.transferTo(oldInfoFile);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Deleting Old Files Error");
				return 2;
			}
			return 0;			
		}
	}
	
	/**
	 * Return:
	 * 0 -正确
	 * 1 -File Error*/
	@Transactional(propagation = Propagation.REQUIRED)
	public PicRecommandResponse.PicRecItems[] GetIndexPicBlogRec() {
		PicRecommandResponse.PicRecItems[] items = new PicRecommandResponse.PicRecItems[4];
		PicRecommandResponse temp = new PicRecommandResponse();
		for(int i=1; i<=4; i++) {
			FileInputStream inputStream;
			try {
				items[i-1] = temp.new PicRecItems();
				inputStream = new FileInputStream(ResourceUtils.getURL("classpath:").getPath()+"Blogs/IndexPicRecommand/"+i+"/info");
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				items[i-1].setBlogId(Integer.valueOf(bufferedReader.readLine()));
				items[i-1].setTitle(bufferedReader.readLine());
				/*ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File(ResourceUtils.getURL("classpath:").getPath()+"Blogs/PicRecommand/"+i+"/pic")));
				ImageIO.write(bufferedImage, "png", outputStream);
				items[i-1].setImage(new String(Base64Coder.encode(outputStream.toByteArray())));*/
				items[i-1].setImage(ResourceUtils.getURL("classpath:").getPath()+"Blogs/IndexPicRecommand/"+i+"/pic.png");
				bufferedReader.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error Occurs When handling files");
			}
			
		}
		return items;
	}
	
	/**
	 * Return:
	 * Not NULL -正确
	 * NULL -File Error*/
	@Transactional(propagation = Propagation.REQUIRED)
	public List<?> GetBlogByAuthor(long uid) {
		List<?> result = this.blogRepository.findByAuthor(uid);
		return result;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
