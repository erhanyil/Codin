package com.codin.Api.Exceptions;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NotFoundException(String entityName){
		super(entityName+" not found!");
	}
	
	public NotFoundException(String entityName, long entityId){
		super(entityName+"["+entityId+"] not found!");
	}
	
}
