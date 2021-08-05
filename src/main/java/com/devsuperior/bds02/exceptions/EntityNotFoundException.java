package com.devsuperior.bds02.exceptions;

public class EntityNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException() {
	}
	
	public EntityNotFoundException(String msg) {
		super(msg);
	}
}
