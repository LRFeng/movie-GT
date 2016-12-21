package com.aring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aring.bean.Movie;
import com.aring.dao.MovieDao;
import com.aring.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService{

	@Autowired
	private MovieDao movieDao;
	
	@Override
	public Movie getMovieById(int id) {
		return movieDao.getMovieById(id);
	}

	@Override
	public void addMovie(Movie movie) {
		movieDao.save(movie);
	}

}
