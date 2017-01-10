package com.aring.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.aring.service.MovieService;

/**
 * 启动服务
 * @author aring
 */
@Service
public class StartService implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private MovieService movieService;
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent()==null){
			
			//启动消费者线程
			Thread consumer = new Thread(new ProcessTask(movieService));
			consumer.start();
			System.out.println("抢票消息队列处理器已启动...");
		}
	}
}


class ProcessTask implements Runnable{

	private MovieService movieService; 
	
	public ProcessTask(MovieService movieService) {
		this.movieService = movieService;
	}
	
	@Override
	public void run() {
		movieService.processSubMessage();
	}
	
}

 


