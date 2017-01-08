package com.aring.dao;

import java.util.List;

import com.aring.bean.Movie;

public interface MovieDao {
	
	public Movie getMovieById(int id);
	
	public void save(Movie movie);
	
	public List<Movie> queryAll();
	
	public Movie update(Movie movie);
	
}
