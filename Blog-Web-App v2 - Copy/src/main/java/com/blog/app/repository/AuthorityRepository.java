package com.blog.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.app.models.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{
	
}
