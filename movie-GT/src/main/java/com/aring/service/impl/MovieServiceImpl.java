package com.aring.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aring.bean.BasicInfo;
import com.aring.bean.MUser;
import com.aring.bean.Movie;
import com.aring.dao.MovieDao;
import com.aring.service.EmailService;
import com.aring.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService{

	@Autowired
	private MovieDao movieDao;
	
	@Autowired
	private EmailService emailService;
	
	@Value(value="http://movie.aringciaran.cn")
	private String domain;
	
	@Override
	public Movie getMovieById(int id) {
		Movie movie =  movieDao.getMovieById(id);
		movie.setPath(domain+movie.getPath());
		return movie;
	}

	@Override
	public void addMovie(Movie movie) {
		movieDao.save(movie);
	}

	@Override
	public List<BasicInfo> getAllBasicInfo() {
		List<Movie> list = movieDao.queryAll();
		List<BasicInfo> basicInfos =  new ArrayList<BasicInfo>(); 
		for (Movie movie : list) {
			movie.setPath(domain+movie.getPath());
			basicInfos.add((BasicInfo)movie);
		}
		return basicInfos;
	}

	@Transactional
	@Override
	public synchronized boolean movieGT(int id,MUser user) throws Exception {
		
		//校验影票数量
		Movie movie =  movieDao.getMovieById(id);
		if(movie.getNumber()==0) {
			throw new Exception("票余额不足");
		}
		
		//更新票数
		try {
			movie.setNumber(movie.getNumber()-1);
			movieDao.update(movie);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据库操作异常");
		}
		
		//发送邮件
		String content=EmailService.EMAIL_CONTEXT1+""
				+ "<a href=\""+domain+"/movie?id="+movie.getId()+"\">"+movie.getName()+"</a><img src=\""+domain+movie.getPath()+"\"/>"
				+ ""+EmailService.EMAIL_CONTEXT2;
		emailService.send(user.getEmail(),EmailService.EMAIL_TITLE, content);
		
		return true;
	}
	
	
	
	

}
