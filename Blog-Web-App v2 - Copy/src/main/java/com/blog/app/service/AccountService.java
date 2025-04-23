package com.blog.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.app.models.Account;
import com.blog.app.models.Authority;
import com.blog.app.repository.AccountRepository;

import utilConstants.Roles;

@Service
public class AccountService implements UserDetailsService{

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Account save(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		if(account.getRole()==null) {
			account.setRole(Roles.USER.getRole());
		}
		
		if(account.getPhoto()==null) {
			String path = "/images/person";
			account.setPhoto(path);
		}
		return accountRepository.save(account);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Account> optionalAccount = accountRepository.findOneByEmailIgnoreCase(email);
		if(!optionalAccount.isPresent()) {
			throw new UsernameNotFoundException("Account Not Found");
		}
		Account account = optionalAccount.get();
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(account.getRole()));
		
		for(Authority _auth: account.getAuthorities()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(_auth.getName()));
		}
		
		// TODO Auto-generated method stub
		return new User(account.getEmail(),account.getPassword(),grantedAuthorities);
	}
	
	public Optional<Account> findOneByEmail(String email){
		return accountRepository.findOneByEmailIgnoreCase(email);
	}
	
	public Optional<Account> findById(long id){
		return accountRepository.findById(id);
	}
	/*
	 * public Optional<Account> findByToken(String token){ return
	 * accountRepository.findOneByToken(token); }
	 */
}
