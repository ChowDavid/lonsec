package com.david.java.demo.lonsec.exception;

public class FileReadingException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3442464991465634117L;

	public FileReadingException(String filename, int lineNumber, String message) {
		super("Error during process file:["+filename+"], line["+lineNumber+"], message["+message+"]");
	}

	
	

}
