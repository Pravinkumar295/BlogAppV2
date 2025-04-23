package com.blog.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.app.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	Optional<Account> findOneByEmailIgnoreCase(String email);
	
	/* Optional<Account> findByPasswordResetToken(String password_reset_token); */
}
