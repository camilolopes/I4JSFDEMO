package com.i4jsfdemo.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.i4jsfdemo.dao.interfaces.TypeDAO;
import com.i4jsfdemo.model.bean.Type;
@Repository
public class TypeDAOImpl extends HibernateDAO<Type, Long> implements TypeDAO{

	public TypeDAOImpl() {
		super(Type.class);
		
	}

	@Override
	public Type findById(Long id) {
		
		return (Type) getCurrentSession().get(Type.class, id);
	}
	@Override
	public List<Type> readAll() {
		
		return (List<Type>) getCurrentSession().createCriteria(Type.class).list();
		
	}
	
}
