package com.i4jsfdemo.services.interfaces;

import java.util.List;

import com.i4jsfdemo.exception.EmailException;
import com.i4jsfdemo.exception.UserException;
import com.i4jsfdemo.model.bean.User;

public interface UserService extends Service<User>{
	
	public void saveOrUpdate(User object)  throws EmailException, UserException;
	
	List<User>readAll();

	List<User> searchUser(String description);
	
	User searchUserByEmail(String email);
}