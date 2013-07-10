package com.i4jsfdemo.services.interfaces;

import java.util.List;

import com.i4jsfdemo.model.bean.Type;

public interface TypeService extends Service<Type> {
	public List<Type> readAll();

}
