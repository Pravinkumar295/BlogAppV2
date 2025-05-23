package com.blog.app.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email(message = "Invalid email")
	@NotEmpty(message = "Email missing")
	private String email;
	
	@NotEmpty(message = "Password missing")
	private String password;
	
	@NotEmpty(message = "Firstname missing")
	private String firstName;
	
	@NotEmpty(message = "Lastname missing")
	private String lastName;
	
	private String gender;
	
	@Min(value = 18)
	@Max(value = 99)
	private int age;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date_of_birth;
	
	private String photo;
	
	private String role;
	
	@Column(name="token")
	private String password_reset_token;
	
	private LocalDateTime password_reset_token_expiry;
	
	@OneToMany(mappedBy = "account")
	private List<Post>posts;
	
	@ManyToMany
	@JoinTable(
			name = "account_authority",
			joinColumns = {@JoinColumn(name="account_id",referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name="authority_id",referencedColumnName = "id")})
	private Set<Authority>authorities = new HashSet<>();
	
}
