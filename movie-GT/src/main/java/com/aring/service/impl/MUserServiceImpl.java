package com.aring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aring.bean.MUser;
import com.aring.dao.MUserDao;
import com.aring.service.MUserService;

@Service
public class MUserServiceImpl implements MUserService{

	@Autowired
	private MUserDao muserDao;
	
	
	@Override
	public MUser addMUser(MUser user) {
		MUser user2 = muserDao.selectById(user.getId());
		if(user2==null){
			muserDao.insert(user);
		}else{
			user.setEmail(user2.getEmail());
			muserDao.update(user);
		}
		return user;
	}

	@Override
	public MUser getMUserById(String id) {
		return muserDao.selectById(id);
	}

	@Override
	public boolean updateMUser(MUser user) {
		return muserDao.update(user);
	}
	
	
	

	
	
}
