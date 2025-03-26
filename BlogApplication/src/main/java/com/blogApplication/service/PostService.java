package com.blogApplication.service;

import com.blogApplication.dto.PostDto;
import com.blogApplication.dto.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto);
	
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
	
	PostDto getPostById(long id);
	
	PostDto updatePost(PostDto postDto, long id);
	
	void deletePost(long id);
	
}
