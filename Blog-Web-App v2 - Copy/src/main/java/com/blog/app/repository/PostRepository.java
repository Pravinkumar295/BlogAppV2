package com.blog.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.app.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}
