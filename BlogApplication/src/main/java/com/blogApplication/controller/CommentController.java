package com.blogApplication.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogApplication.dto.CommentDto;
import com.blogApplication.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}
	
	@PostMapping("/posts/{id}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable long id, 
			@Valid @RequestBody CommentDto commentDto){
		
		
		return new ResponseEntity<>(commentService.createComment(id, commentDto), HttpStatus.CREATED);
	}
	
	@GetMapping("/posts/{id}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable long id){
		
		return commentService.getCommentsByPostId(id);
	}
	
	@GetMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId,
			                                         @PathVariable(value = "id") long commentId) {
		CommentDto commentDto = commentService.getCommentById(postId, commentId);
		
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId,
			           								@PathVariable(value = "id") long commentId, 
			           								@Valid @RequestBody CommentDto commentDto){
		CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
		
		return new ResponseEntity<>(updatedComment, HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") long postId, 
												@PathVariable(value = "id") long commentId){
		commentService.deleteComment(postId, commentId);
		
		return new ResponseEntity<>("Comment Deleted Successfully!!", HttpStatus.OK);
	}
	
}
