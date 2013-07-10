package com.i4jsfdemo.dao.interfaces;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface GenericDAO<T,Type extends Serializable> {
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	void saveOrUpdate(T entity);
	@Transactional
	void delete(T entity);
	@Transactional
	List<T> readAll();
}
