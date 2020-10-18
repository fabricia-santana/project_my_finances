package com.briciasantana.myfinances.service;

import com.briciasantana.myfinances.model.entity.User;

public interface UserService {
	
	User authenticate(String email, String password);
		
	User saveUser(User user);
	
	void validateEmail(String email);

}
