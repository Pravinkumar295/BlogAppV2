package com.blog.app.config;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.blog.app.models.Account;
import com.blog.app.models.Authority;
import com.blog.app.models.Post;
import com.blog.app.service.AccountService;
import com.blog.app.service.AuthorityService;
//import com.blog.app.service.AccountService;
import com.blog.app.service.PostService;

import utilConstants.Privillages;
import utilConstants.Roles;

@Component
public class SeedData implements CommandLineRunner{

	
	  @Autowired private AccountService accountService;
	 
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Override
	public void run(String... args) throws Exception {
		
		for(Privillages auth : Privillages.values()) {
			Authority authority = new Authority();
			authority.setId(auth.getId());
			authority.setName(auth.getPrivillage());
			authorityService.save(authority);
		}
		
		Account account01 = new Account();
		Account account02 = new Account();
		Account account03 = new Account();
		Account account04 = new Account();
		
		account01.setEmail("pk5586067@gmail.com");
		account01.setPassword("pass@123");
		account01.setFirstName("user");
		account01.setLastName("lastname");
		account01.setAge(19);
		account01.setDate_of_birth(LocalDate.parse("1990-01-01"));
		account01.setGender("Male");
		
		account02.setEmail("admin@admin.com");
		account02.setPassword("pass@123");
		account02.setFirstName("admin");
		account02.setLastName("lastname");
		account02.setRole(Roles.ADMIN.getRole());
		account02.setAge(19);
		account02.setDate_of_birth(LocalDate.parse("1990-01-01"));
		account02.setGender("Male");
		
		account03.setEmail("editor@editor.com");
		account03.setPassword("pass@123");
		account03.setFirstName("editor");
		account03.setLastName("lastname");
		account03.setRole(Roles.EDITOR.getRole());
		account03.setAge(19);
		account03.setDate_of_birth(LocalDate.parse("1990-01-01"));
		account03.setGender("Male");
		
		account04.setEmail("super_editor@editor.com");
		account04.setPassword("pass@123");
		account04.setFirstName("super_editor");
		account04.setLastName("lastname");
		account04.setRole(Roles.EDITOR.getRole());
		account04.setAge(19);
		account04.setDate_of_birth(LocalDate.parse("1990-01-01"));
		account04.setGender("Male");
		
		Set<Authority> authorities = new HashSet<>();
		authorityService.findById(Privillages.RESET_ANY_USER_PASSWORD.getId()).ifPresent(authorities::add);
		authorityService.findById(Privillages.ACCESS_ADMIN_PANEL.getId()).ifPresent(authorities::add);
		account04.setAuthorities(authorities);
		
		
		accountService.save(account01);
		accountService.save(account02);
		  accountService.save(account03); accountService.save(account04);
		 
		// TODO Auto-generated method stub
		List<Post> posts = postService.getAll();
		if(posts.size()== 0) {
			Post post01 = new Post();
			post01.setTitle("The Forgotten Valley");
			post01.setBody("In the heart of the rugged mountains, there lay a valley forgotten by time. "
					+ "Its entrance veiled by dense foliage and mist, it remained untouched by the chaos of the modern world.");
			post01.setAccount(account03);
			postService.save(post01);
			
			Post post02 = new Post();
			post02.setTitle("Echoes of the Lost Valley");
			post02.setBody("Deep within the secluded mountains lies a forgotten valley, hidden from the world by nature's embrace."
					+ " Legends tell of ancient civilizations and mysteries veiled in mist. ");
			post02.setAccount(account04);
			postService.save(post02);
			
			Post post03 = new Post();
			post03.setTitle("The Enigmatic Vale");
			post03.setBody("Tucked away in rugged mountains lies a secluded valley, untouched by time. "
					+ "Legends speak of its ancient past and the secrets it guards. Though few dare to explore its depths. ");
			post03.setAccount(account04);
			postService.save(post03);
			
			Post post04 = new Post();
			post04.setTitle("The Forgotten Valley");
			post04.setBody("In the heart of the rugged mountains, there lay a valley forgotten by time. "
					+ "Its entrance veiled by dense foliage and mist, it remained untouched by the chaos of the modern world. ");
			post04.setAccount(account03);
			postService.save(post04);
			
			Post post05 = new Post();
			post05.setTitle("Echoes of the Lost Valley");
			post05.setBody("\"Deep within the secluded mountains lies a forgotten valley, hidden from the world by nature's embrace. Legends tell of ancient civilizations and mysteries veiled in mist. its secrets.");
			post05.setAccount(account04);
			postService.save(post05);
			
			Post post06 = new Post();
			post06.setTitle("The Enigmatic Vale");
			post06.setBody("Tucked away in rugged mountains lies a secluded valley, untouched by time. "
					+ "Legends speak of its ancient past and the secrets it guards. Though few dare to explore its depths ");
			post06.setAccount(account04);
			postService.save(post06);
		}
	}

}
