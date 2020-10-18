package com.briciasantana.myfinances.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.briciasantana.myfinances.exception.ErroAuthenticateException;
import com.briciasantana.myfinances.exception.RegraNegocioException;
import com.briciasantana.myfinances.model.entity.User;
import com.briciasantana.myfinances.model.repository.UserRepository;
import com.briciasantana.myfinances.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	private UserRepository repository;
	
	@Autowired
	public UserServiceImpl(UserRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public User authenticate(String email, String password) {
		Optional<User> user = repository.findByEmail(email);
		
		if(!user.isPresent()) {
			throw new ErroAuthenticateException("Usuário não encontrado para o email informado.");
		}
		
		if(!user.get().getPassword().equals(password)) {
			throw new ErroAuthenticateException("Senha inválida.");
		}
		
		return user.get();
	}
	

	@Override
	@Transactional
	public User saveUser(User user) {
		validateEmail(user.getEmail());		
		return repository.save(user);
	}

	@Override
	public void validateEmail(String email) {
		
		boolean exists = repository.existsByEmail(email);
		if(exists) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com esse e-mail");
		}
		
	}

	
}
