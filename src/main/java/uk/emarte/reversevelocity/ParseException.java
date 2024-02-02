/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.reversevelocity;

public class ParseException extends Exception {
	public ParseException(String string, Exception ex) {
		super(string, ex);
	}
}
