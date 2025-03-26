package com.blogApplication.dto;

import lombok.Getter;

@Getter
public class ErrorDetails {
	private java.util.Date timestamp;
	private String message;
	private String details;
	
	public ErrorDetails(java.util.Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	
}
