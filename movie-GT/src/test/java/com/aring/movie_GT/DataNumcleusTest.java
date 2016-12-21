package com.aring.movie_GT;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.datanucleus.api.jpa.PersistenceProviderImpl;
import org.junit.Test;

import com.aring.bean.BasicInfo;
import com.aring.bean.Movie;

public class DataNumcleusTest {
	
	@Test
	public void insert(){
		//persistenceProvider
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Movie");
		EntityManager manager = factory.createEntityManager();
		
		EntityTransaction ts = manager.getTransaction();
		try{
			ts.begin();
			Movie movie = new Movie();
			movie.setId(0);
			movie.setName("你的名字");
			movie.setDirector("新城武");
			manager.persist(movie);
			ts.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(ts.isActive()){
				ts.rollback();
			}
			manager.close();
		}
		
	}
	
	
	@Test
	public void query(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Movie");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction ts = manager.getTransaction();
		try{
			ts.begin();
			BasicInfo movie = manager.find(BasicInfo.class,6);
			System.out.println(movie);
			ts.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(ts.isActive()){
				ts.rollback();
			}
			manager.close();
		}
		
	}
	

}
