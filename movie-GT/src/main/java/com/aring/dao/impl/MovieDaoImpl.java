package com.aring.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aring.bean.ImageInfo;
import com.aring.bean.MOrder;
import com.aring.bean.Movie;
import com.aring.dao.MovieDao;

@Repository
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

	@Override
	@Transactional
	public List<Movie> queryAll() {
		Query query = em.createQuery("SELECT m FROM movie m");
		List results = query.getResultList();
	    Iterator iter = results.iterator();
	    List<Movie> movies = new ArrayList<Movie>();
	    while (iter.hasNext())
	    {
	        Movie movie = (Movie)iter.next();
	        movies.add(movie);
	    }
		return movies;
	}

	@Override
	public Movie update(Movie movie) {
		return em.merge(movie);
	}

	@Override
	@Transactional
	public void saveOrder(MOrder order) {
		em.persist(order);
	}

	@Override
	public MOrder selectOrder(MOrder order) {
		
		String sql = "SELECT m FROM MOrder m where m.uid = '"+order.getUid()
		+"' and m.mid="+order.getMid();
		Query query = em.createQuery(sql);
		List results = query.getResultList();
	    Iterator iter = results.iterator();
	    List<MOrder> orders = new ArrayList<MOrder>();
	    while (iter.hasNext())
	    {
	    	MOrder movie = (MOrder)iter.next();
	        orders.add(movie);
	    }
	    if(orders.size()==0) return null;
	    else return orders.get(0);
	}

	@Override
	public List<ImageInfo> selectImageInfo(int mid) {
		String sql = "select m from ImageInfo m where m.mid="+mid;
		Query query = em.createQuery(sql);
		List results = query.getResultList();
		Iterator iter = results.iterator();
		List<ImageInfo> infos = new ArrayList<>();
		while(iter.hasNext()){
			ImageInfo info = (ImageInfo) iter.next();
			infos.add(info);
		}
		return infos;
	}

}
