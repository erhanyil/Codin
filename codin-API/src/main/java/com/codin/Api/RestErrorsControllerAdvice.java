package com.codin.Api;

import com.codin.Api.Exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestErrorsControllerAdvice
{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * This handler transforms any NotFoundException thrown by controllers into a 404 HTTP response
	 * @param ex
	 */
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND,
		reason="Error: not found!")
	public void handleException(NotFoundException ex){
		log.debug("Handling NotFoundException: "+ex.getMessage());
	}
	
	/**
	 * This handler transforms any IllegalArgumentException thrown by controllers into a 400 HTTP response
	 * @param ex
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST,
		reason="Error: bad request!")
	public void handleException(IllegalArgumentException ex){
		log.debug("Handling IllegalArgumentException: "+ex.getMessage());
	}
}