package com.blogApplication.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blogApplication.dto.CommentDto;
import com.blogApplication.entity.Comment;
import com.blogApplication.entity.Post;
import com.blogApplication.exception.BlogAPIException;
import com.blogApplication.exception.ResourceNotFoundException;
import com.blogApplication.repository.CommentRepository;
import com.blogApplication.repository.PostRepository;
import com.blogApplication.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		super();
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public CommentDto createComment(long id, CommentDto commentDto) {
		Comment comment = mapToEntity(commentDto);

		// retrive post by id
		Post post = postRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Post", "id", id));
		// set post to comment entity
		comment.setPost(post);
		//comment entity to database
		Comment newComment = commentRepository.save(comment);
		
		return mapToDTO(newComment);
	}
	
	
	@Override
	public List<CommentDto> getCommentsByPostId(long id) {
		// retrive comments by post id
		List<Comment> comments = commentRepository.findByPostId(id);
		
		
		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
	}
	
	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post", "id", postId));
		// retrive comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(
				() -> new ResourceNotFoundException("comment", "id", commentId));
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, 
					"Comment does not belongs to perticular post");
		}
		return mapToDTO(comment);
	}
	
	
	
	
	private CommentDto mapToDTO(Comment comment) {
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
		
//		CommentDto commentDto = new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());

		return commentDto;
	}

	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = mapper.map(commentDto, Comment.class);
		
//		Comment comment = new Comment();
//		comment.setId(commentDto.getId());
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());

		return comment;
	}

	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
		// retrive post from database
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post", "id", postId));
		
		// retrive comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(
				() -> new ResourceNotFoundException("comment", "id", commentId));
		
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,
					"Comment Does Not Belongs to perticular post");
		}
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		Comment updatedComment = commentRepository.save(comment);
		return mapToDTO(updatedComment);
	}

	@Override
	public void deleteComment(long postId, long commentId) {
		// retrive post from database
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		// retrive comment by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
		
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, 
					"Comment Does Not Belongs to perticular post");
		}
		
		commentRepository.delete(comment);
	}

	

	

}
