package com.blogApplication.service;

import java.util.List;

import com.blogApplication.dto.CommentDto;

public interface CommentService {
	
	CommentDto createComment(long id, CommentDto commentDto);
	
	List<CommentDto> getCommentsByPostId(long id);
	
	CommentDto getCommentById(Long postId, Long commentId);
	
	CommentDto updateComment(long postId, long commentId, CommentDto commentDto);
	
	void deleteComment(long postId, long commentId);
}
