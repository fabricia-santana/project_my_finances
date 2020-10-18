package com.briciasantana.myfinances.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.briciasantana.myfinances.model.entity.User;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void verificateIfExistEmail() {
		
		User user = User.builder().name("user").email("user@email.com").build();
		repository.save(user);
		
		boolean result = repository.existsByEmail("user@email.com");
		
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void returnFalseWhenDoesntExistUserSavedWithEmail() {	
		
		boolean result = repository.existsByEmail("user@email.com");
		
		Assertions.assertThat(result).isFalse();		
	}
	
	@Test
	public void saveOneUserInDataBase() {
		
		User user = createUser();
		
		User userSaved = repository.save(user);
		
		Assertions.assertThat(userSaved.getId()).isNotNull();
	}
	
	@Test
	public void getUserByEmail() {
		
		User user = createUser();
		entityManager.persist(user);
		
		Optional<User> result = repository.findByEmail("user@email.com");
		
		Assertions.assertThat(result.isPresent()).isTrue();
	}
	
	@Test
	public void getAndReturNullUserByEmailWhenDontExistsInDataBase() {
		
		Optional<User> result = repository.findByEmail("user@email.com");
		
		Assertions.assertThat(result.isPresent()).isFalse();
	}

	public static User createUser() {
		return User
				.builder()
				.name("user")
				.email("user@email.com")
				.password("password")
				.build();
	}
}
