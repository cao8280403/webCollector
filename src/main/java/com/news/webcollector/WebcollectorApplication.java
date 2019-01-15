package com.news.webcollector;

import com.news.webcollector.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebcollectorApplication implements CommandLineRunner {
	@Autowired
	private NewsService newsService;
	public static void main(String[] args) {
		SpringApplication.run(WebcollectorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("系统启动。。。。。。");
//		newsService.findAll();
	}
}
