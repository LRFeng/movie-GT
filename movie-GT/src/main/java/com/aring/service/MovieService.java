package com.aring.service;

import com.aring.bean.Movie;

public interface MovieService {

	public Movie getMovieById(int id);
	
	public void addMovie(Movie movie);
}
