package com.i4jsfdemo.services.interfaces;

import com.i4jsfdemo.exception.EmailException;
import com.i4jsfdemo.exception.UserException;


public interface LoginService {
	public void authenticate(String email, String password) throws EmailException, UserException;
}
