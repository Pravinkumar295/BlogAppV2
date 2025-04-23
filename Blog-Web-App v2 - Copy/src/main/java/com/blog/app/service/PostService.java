package com.blog.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.blog.app.models.Post;
import com.blog.app.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	public PostRepository postRepository;
	
	
	
	public PostService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public PostService(PostRepository postRepository) {
		super();
		this.postRepository = postRepository;
	}

	public Optional<Post> getById(Long id){
		return postRepository.findById(id);
	}
	
	public Page<Post> findAll(int offset,int pageSize,String field){
		return postRepository.findAll(PageRequest.of(offset, pageSize).withSort(Direction.ASC, field));
	}
	
	public List<Post>getAll(){
		return postRepository.findAll();
	}
	
	public void delete(Post post) {
		postRepository.delete(post);
	}
	
	public Post save(Post post) {
		if(post.getId() == null) {
			post.setCreatedAt(LocalDateTime.now());
		}
		post.setUpdatedAt(LocalDateTime.now());
		
		return postRepository.save(post);
	}
}
