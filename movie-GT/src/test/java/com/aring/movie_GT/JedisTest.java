package com.aring.movie_GT;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	private JedisPool jedisPool;
	
	@Before
	public void befor(){
		jedisPool =new JedisPool("123.207.246.80", 6379);
	}
	
	@Test
	public void setTest(){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set("setTest", "aring");
			System.out.println(jedis.get("setTest"));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(jedis!=null){
				jedis.close();
			}
		}
		
	}
	
	
	
}
