package com.aring.movie_GT;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Test;

import com.aring.bean.MOrder;

public class DataNumcleusTest {
	
/*	@Test
	public void insert(){
		//persistenceProvider
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Movie");
		EntityManager manager = factory.createEntityManager();
		
		EntityTransaction ts = manager.getTransaction();
		try{
			ts.begin();
			Movie movie = new Movie();
			movie.setName("你的名字");
			movie.setDirector("新城武");
			//manager.persist(movie);
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
		
	}*/
	
	@Test
	public void insertOrder(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Movie");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction ts = manager.getTransaction();
		try{
			ts.begin();

			String sql = "SELECT m FROM MOrder m where m.uid = '3798613182"
			+"' and m.mid=2";
			Query query = manager.createQuery(sql);
			List results = query.getResultList();
		    Iterator iter = results.iterator();
		    List<MOrder> orders = new ArrayList<MOrder>();
		    while (iter.hasNext())
		    {
		    	MOrder order = (MOrder)iter.next();
		        orders.add(order);
		        System.out.println(order);
		    }
		    
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
