package com.david.java.demo.lonsec.exception;

public class ConfigException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6507194066753417970L;

	public ConfigException(Throwable e) {
		super(e);
	}

	public ConfigException(String message) {
		super(message);
	}

	

}
