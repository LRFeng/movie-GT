package com.aring.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.aring.dao.RedisDao;

@Repository
public class RedisDaoImpl implements RedisDao{

	@Autowired
	private RedisTemplate<String,String> template;
	
	@Override
	public Integer getMovieNumber(String id) {
		String value = template.execute(new RedisCallback<String>() {

			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = template.getStringSerializer().serialize(id);
				byte[] value =connection.get(key);
				if(value==null) {
					return null;
				}else{
					return template.getStringSerializer().deserialize(value);
				}
			}
		
		});
		if(value==null) return null;
		else return Integer.valueOf(value);
	}

	@Override
	public void setMovieNumber(String id, String number) {
		template.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = template.getStringSerializer().serialize(id);
				byte[] value = template.getStringSerializer().serialize(number);
				connection.set(key,value);
				return true;
			}
		});
	}

	
	@Override
	public void lpush(String key, String mid, String uid) {
		template.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keys = template.getStringSerializer().serialize(key);
				byte[] values = template.getStringSerializer().serialize(mid+""+uid);
				return connection.lPush(keys,values);
			}
		});
	}

	@Override
	public String lpop(String key) {
		String result = template.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keys = template.getStringSerializer().serialize(key);
				byte[] values = connection.lPop(keys);
				return template.getStringSerializer().deserialize(values);
			}
		});
		return result;
	}
}
