package com.blog.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import utilConstants.Privillages;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class WebSecurityConfig {
	public static final String[] WHITE_LIST = {
			"/",
			"/login",
			"/register",
			"/css/**",
			"/fonts/**",
			"/images/**",
			"/js/**"
	};
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		.authorizeRequests()
		.requestMatchers(WHITE_LIST)
		.permitAll()
		.requestMatchers("/profile/**").authenticated()
		.requestMatchers("/admin**").hasRole("ADMIN")
		.requestMatchers("/editor/**").hasAnyRole("ADMIN","EDITOR")
		.requestMatchers("/admin/**").hasAuthority(Privillages.ACCESS_ADMIN_PANEL.getPrivillage())
		.and()
		.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/login")
		.usernameParameter("email")
		.passwordParameter("password")
		.defaultSuccessUrl("/",true)
		.failureUrl("/login?error")
		.permitAll()
		.and()
		.logout()
		.logoutSuccessUrl("/")
		.and()
		.rememberMe().rememberMeParameter("remember-me")
		.and()
 		.httpBasic();
		
		
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
		return http.build();
	}
}
