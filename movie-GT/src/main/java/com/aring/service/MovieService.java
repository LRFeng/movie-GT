package com.aring.service;

import java.util.List;

import com.aring.bean.BasicInfo;
import com.aring.bean.MOrder;
import com.aring.bean.MUser;
import com.aring.bean.Movie;

public interface MovieService {

	/**
	 * 获取电影的所有概要信息
	 * @return
	 */
	public List<BasicInfo> getAllBasicInfo(); 
	
	
	public Movie getMovieById(int id);
	
	public List<String> getMovieImage(int mid);
	
	public void addMovie(Movie movie);
	
	public Integer getMovieNumber(int id);
	
	public void updateNumber(int id);
	
	public boolean movieGT(int id,MUser user) throws Exception;
	
	public boolean movieGT2(int id,MUser user) throws Exception;
	
	/**
	 * 预定电影消息处理
	 */
	public void processSubMessage();
	
	
	public MOrder getOrder(String uid,Integer mid);
	
	
	
}
