package com.hackathon.alert.twitter.auth;

public class TwitterAuthenticationException extends Exception {

	private static final long serialVersionUID = 6861671993860466952L;

	public TwitterAuthenticationException() {
		super();
	}
	
	public TwitterAuthenticationException(final String message) {
		super(message);
	}
	
	public TwitterAuthenticationException(final String message, final Throwable t) {
		super(message, t);
	}
	
	public TwitterAuthenticationException(final Throwable t) {
		super(t);
	}
}
