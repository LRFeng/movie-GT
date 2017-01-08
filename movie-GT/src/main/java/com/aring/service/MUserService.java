package com.aring.service;

import com.aring.bean.MUser;

public interface MUserService {

	public MUser addMUser(MUser user);
	
	public MUser getMUserById(String id);
	
	public boolean updateMUser(MUser user);
	
	
}
