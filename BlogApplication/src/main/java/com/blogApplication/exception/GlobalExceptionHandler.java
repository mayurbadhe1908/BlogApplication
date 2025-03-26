package com.blogApplication.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.blogApplication.dto.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler {
	// handle specific exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
																		WebRequest webRequest){
			ErrorDetails errorDetails = new ErrorDetails(new java.util.Date(), exception.getMessage(),
					webRequest.getDescription(false));
			
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BlogAPIException.class)
	public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException exception,
																		WebRequest webRequest){
			ErrorDetails errorDetails = new ErrorDetails(new java.util.Date(), exception.getMessage(),
					webRequest.getDescription(false));
			
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception,
																		WebRequest webRequest){
			ErrorDetails errorDetails = new ErrorDetails(new java.util.Date(), exception.getMessage(),
					webRequest.getDescription(false));
			
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}
	
	// global exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
			                                                  WebRequest webRequest){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), 
				    webRequest.getDescription(false));
		
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
