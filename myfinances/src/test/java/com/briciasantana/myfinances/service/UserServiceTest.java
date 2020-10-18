package com.briciasantana.myfinances.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.briciasantana.myfinances.exception.ErroAuthenticateException;
import com.briciasantana.myfinances.exception.RegraNegocioException;
import com.briciasantana.myfinances.model.entity.User;
import com.briciasantana.myfinances.model.repository.UserRepository;
import com.briciasantana.myfinances.service.impl.UserServiceImpl;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserServiceTest {

	@SpyBean
	UserServiceImpl service;
	
	@MockBean
	UserRepository repository;
	
	@Test(expected = Test.None.class)
	public void saverUser() {
		Mockito.doNothing().when(service).validateEmail(Mockito.anyString());
		
		User user = User
				.builder()
				.id(1l)
				.name("name")
				.email("email@email.com")
				.password("password").build();
		
		Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
		
		User userSaved = service.saveUser(new User());
		
		Assertions.assertThat(userSaved).isNotNull();
		Assertions.assertThat(userSaved.getId()).isEqualTo(1l);
		Assertions.assertThat(userSaved.getName()).isEqualTo("name");
		Assertions.assertThat(userSaved.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(userSaved.getPassword()).isEqualTo("password");
	}
	
	@Test(expected = RegraNegocioException.class)
	public void notSaveUserWithEmailSaved() {
		String email = "email@email.com";
		User user = User.builder().email(email).build();
		
		Mockito.doThrow(RegraNegocioException.class).when(service).validateEmail(email);
		
		service.saveUser(user);
		
		Mockito.verify(repository, Mockito.never()).save(user);
	}
	
	@Test(expected = Test.None.class)
	public void authenticateUserWithSuccess() {
		String email = "email@email.com";
		String password = "password";
		
		User user = User.builder().email(email).password(password).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(user));
		
		User result = service.authenticate(email, password);
		
		Assertions.assertThat(result).isNotNull();
	}
	
	@Test
	public void erroWhenDontFindUserSavedWithEmailSended() {
		
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		Throwable exception = Assertions.catchThrowable(() -> service.authenticate("email@email.com", "password"));
		
		Assertions.assertThat(exception)
			.isInstanceOf(ErroAuthenticateException.class)
			.hasMessage("Usuário não encontrado para o email informado.");
	} 
	
	@Test(expected = Test.None.class)
	public void validateEmail() {
		
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		service.validateEmail("email@email.com");		
	}
	
	@Test
	public void erroWhenPasswordDifferent() {
		
		String pasword = "password";
		User user = User.builder().email("email@email.com").password(pasword).build();
		
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
		
		Throwable exception = Assertions.catchThrowable(() -> service.authenticate("email@email.com", "123"));
		
		Assertions.assertThat(exception).isInstanceOf(ErroAuthenticateException.class).hasMessage("Senha inválida.");
	}
	
	@Test(expected = RegraNegocioException.class)
	public void returnErrorValidateEmailWhenExistsEmailSaved() {
				
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		service.validateEmail("email@email.com");
	}
	
	
	
	
}
