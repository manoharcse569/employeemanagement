package com.manohar.exception;

public class CSVParsingException extends RuntimeException {
	
	String message;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CSVParsingException(String message) {
		super(message);
	}
	
	public CSVParsingException(String message, Throwable throwable) {
		super(message, throwable);
	}

	
}
