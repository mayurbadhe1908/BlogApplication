package com.blogApplication.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentDto {
	
	private long id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String email;
	@NotEmpty
	private String body;
}
