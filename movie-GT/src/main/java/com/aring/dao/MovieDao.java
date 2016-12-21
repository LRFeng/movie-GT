package com.aring.dao;

import com.aring.bean.Movie;

public interface MovieDao {
	
	public Movie getMovieById(int id);
	
	public void save(Movie movie);
	
	
}
