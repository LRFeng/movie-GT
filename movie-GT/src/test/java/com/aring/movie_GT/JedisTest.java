package com.aring.movie_GT;

import java.awt.List;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

public class JedisTest {

	private JedisPool jedisPool;
	
	@Before
	public void befor(){
		jedisPool =new JedisPool("123.207.246.80", 6379);
	}
	
	/**
	 * 测试脏数据
	 */
	@Test
	public void testErrorData(){
		Jedis jedis = jedisPool.getResource();
		jedis.auth("123456");
		
	}
	
	@Test
	public void testLpush(){
		Jedis jedis = jedisPool.getResource();
		jedis.auth("123456");
		/*jedis.lpush("111","test--");
		jedis.lpush("111","test--");*/
		System.out.println(jedis.lpop("111"));
	}
	
	
/*	
	@Test
	public void setTest(){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.auth("123456");
			Transaction transaction = jedis.multi();
			jedis.set("setTest", "aring");
			System.out.println(jedis.get("setTest"));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(jedis!=null){
				jedis.close();
			}
		}
		
	}*/
	
	
public static void main(String[] args) {
	JedisPool jedisPool =new JedisPool("123.207.246.80", 6379);
	for (int i = 0; i < 100; i++) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth("123456");
		new MyThread(jedis).start();
	}
}


public static synchronized void tt(Jedis jedis,String name){
	int number = Integer.valueOf(jedis.get("aring"));
	if(number>0){
		number--;
		jedis.set("aring", number+"");
		System.out.println(name+"抢到一张票");
	}else{
		System.out.println(name+"没有抢到票");
	}
	jedis.close();
}
	
}

class MyThread extends Thread{
	
	private Jedis jedis;
	
	public MyThread(Jedis jedis) {
		this.jedis = jedis;
	}
	
	@Override
	public void run() {
		JedisTest.tt(jedis,this.getName());
	}
	
}	
