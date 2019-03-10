package com.SunBlog.BlogSiteBackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BlogSiteBackEndApplication extends SpringBootServletInitializer{

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
		return applicationBuilder.sources(BlogSiteBackEndApplication.class);
	}*/
	public static void main(String[] args) {
		SpringApplication.run(BlogSiteBackEndApplication.class, args);
	}

}

