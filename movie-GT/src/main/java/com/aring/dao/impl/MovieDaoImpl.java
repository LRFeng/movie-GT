package com.aring.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aring.bean.Movie;
import com.aring.dao.MovieDao;

@Repository
@Transactional(transactionManager="transactionManager")
public class MovieDaoImpl  implements MovieDao{
	
	@PersistenceContext
    protected EntityManager em;
	
	@Override
	public Movie getMovieById(int id) {
		return em.find(Movie.class, id);
	}

	@Override
	@Transactional
	public void save(Movie movie) {
		em.persist(movie);
	}

}
