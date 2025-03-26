package com.blogApplication.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.blogApplication.dto.PostDto;
import com.blogApplication.dto.PostResponse;
import com.blogApplication.entity.Post;
import com.blogApplication.exception.ResourceNotFoundException;
import com.blogApplication.repository.PostRepository;
import com.blogApplication.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	
	private PostRepository postRepository;
	
	private ModelMapper mapper;
		
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
		this.postRepository=postRepository;
		this.mapper=mapper;
	}



	@Override
	public PostDto createPost(PostDto postDto) {
		
		//convert DTO to Entity
		Post post = mapToEntity(postDto);
		Post newPost = postRepository.save(post);
		
		
		//convert entity to DTO
		PostDto postResponse = mapToDto(newPost);
		return postResponse;
	}



	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
			Sort.by(sortBy).descending();
		
		
		org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		org.springframework.data.domain.Page<Post> posts = postRepository.findAll(pageable);
		
		List<Post> listOfPosts = posts.getContent();
		
		List<PostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
	}
	
	//convert Entity into DTO
	private PostDto mapToDto(Post post) {
		PostDto postDto = mapper.map(post, PostDto.class);
		
//		PostDto postDto = new PostDto();
//		postDto.setId(post.getId());
//		postDto.setTitle(post.getTitle());
//		postDto.setDescription(post.getDescription());
//		postDto.setContent(post.getContent());
		
		return postDto;
	}
	
	//convert DTO to Entity
	private Post mapToEntity(PostDto postDto) {
		Post post = mapper.map(postDto, Post.class);
		
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
//		
		return post;
	}



	@Override
	public PostDto getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		return mapToDto(post);
	}

	
	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		// get post by id from the database
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		Post savedPost = postRepository.save(post);
		return mapToDto(savedPost);
	}



	@Override
	public void deletePost(long id) {
		postRepository.deleteById(id);
	}

}
