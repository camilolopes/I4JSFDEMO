package com.i4jsfdemo.dao.interfaces;

import org.springframework.transaction.annotation.Transactional;

import com.i4jsfdemo.model.bean.Type;

public interface TypeDAO extends GenericDAO<Type, Long>{
	@Transactional
	Type findById(Long id);
	
}
