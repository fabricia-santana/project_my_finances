package com.briciasantana.myfinances.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.briciasantana.myfinances.api.dto.UserDTO;
import com.briciasantana.myfinances.exception.ErroAuthenticateException;
import com.briciasantana.myfinances.exception.RegraNegocioException;
import com.briciasantana.myfinances.model.entity.User;
import com.briciasantana.myfinances.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserResource {
	
	private UserService service;
	
	public UserResource(UserService service) {
		this.service = service;
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity authenticate(@RequestBody UserDTO dto) {
		
		try {
			User userAthenticated = service.authenticate(dto.getEmail(), dto.getPassword());
			return ResponseEntity.ok(userAthenticated);
			
		}catch(ErroAuthenticateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity save (@RequestBody UserDTO dto) {
		
		User user = User
				.builder()
				.name(dto.getName())
				.email(dto.getEmail())
				.password(dto.getPassword()).build();
		
		try {
			User userSaved = service.saveUser(user);
			return new ResponseEntity(userSaved, HttpStatus.CREATED);
		}catch(RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
