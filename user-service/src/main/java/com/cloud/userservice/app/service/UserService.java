package com.cloud.userservice.app.service;

import java.util.Date;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.cloud.userservice.app.dao.UserDao;
import com.cloud.userservice.app.domain.UserEntity;
import com.jedlab.framework.spring.service.AbstractCrudService;

@Service
public class UserService extends AbstractCrudService<UserEntity> {

	UserDao dao;
	
	@PersistenceContext
	EntityManager em;

	public UserService(UserDao repository, PlatformTransactionManager ptm) {
		super(repository, ptm);
		this.dao = repository;
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
//	public void generate100Users()
//	{
//		Random;
//		userda
//	}
	
	@Override
	protected void beforeInsert(UserEntity entity) {
		entity.setCreatedDate(new Date());
	}
	
	@Override
	protected void afterInsert(UserEntity entity, boolean success) {
	}
	
	
	@Override
	protected void beforeUpdate(UserEntity entity) {
		// TODO Auto-generated method stub
		super.beforeUpdate(entity);
	}
	

}
