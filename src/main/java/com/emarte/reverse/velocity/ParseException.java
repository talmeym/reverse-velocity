package com.emarte.reverse.velocity;

@SuppressWarnings("serial")
public class ParseException extends Exception {
	public ParseException(String string, Exception ex) {
		super(string, ex);
	}
}
