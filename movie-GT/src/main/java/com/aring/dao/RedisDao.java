package com.aring.dao;

public interface RedisDao {

	public Integer getMovieNumber(String id);  
	
	public void setMovieNumber(String id,String number);
	
	/**
	 * 
	 * @param key
	 * @param mid 电影ID
	 * @param uid 用户ID
	 */
	public void lpush(String key,String mid,String uid);
	
	public String lpop(String key);
}
