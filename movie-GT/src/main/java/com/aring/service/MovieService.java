package com.aring.service;

import java.util.List;

import com.aring.bean.BasicInfo;
import com.aring.bean.MUser;
import com.aring.bean.Movie;

public interface MovieService {

	/**
	 * 获取电影的所有概要信息
	 * @return
	 */
	public List<BasicInfo> getAllBasicInfo(); 
	
	
	public Movie getMovieById(int id);
	
	public void addMovie(Movie movie);
	
	public boolean movieGT(int id,MUser user) throws Exception;
	
}
