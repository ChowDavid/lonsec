package com.david.java.demo.lonsec.exception;

public class IncorrectDataException extends Exception{



	/**
	 * 
	 */
	private static final long serialVersionUID = -5335845444221221256L;

	public IncorrectDataException(String filename, int lineNumber, String message) {
		super("Error during process file:["+filename+"], line["+lineNumber+"], message["+message+"]");
	}


}
