package com.blog.app.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotBlank(message = "Missing Post Title")
	private String title;
	
	@Column()
	@NotBlank(message = "Missing Post Body")
	private String body;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	
	  @ManyToOne
	  
	  @JoinColumn(name = "account_id",referencedColumnName = "id",nullable = false)
	  private Account account;
	 
}
