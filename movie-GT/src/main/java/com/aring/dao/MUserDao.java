package com.aring.dao;

import com.aring.bean.MUser;

public interface MUserDao {

	public boolean insert(MUser user);
	
	public MUser selectById(String id);
	
	public boolean update(MUser user);
	
	
}
