package com.blogApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogApplication.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
}
