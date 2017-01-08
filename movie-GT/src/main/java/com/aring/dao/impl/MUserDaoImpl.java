package com.aring.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aring.bean.MUser;
import com.aring.dao.MUserDao;

@Repository
public class MUserDaoImpl implements MUserDao{

	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	@Transactional
	public boolean insert(MUser user) {
		em.persist(user);
		return true;
	}

	@Override
	public MUser selectById(String id) {
		return em.find(MUser.class, id);
	}

	@Override
	@Transactional
	public boolean update(MUser user) {
		em.merge(user);
		return true;
	}

}
