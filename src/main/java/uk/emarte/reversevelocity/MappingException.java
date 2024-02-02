/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.reversevelocity;

// this is purposely a runtime exception as mappers are called from within a sax handler,
// so we cannot throw a checked exception from the implemented interfaces
@SuppressWarnings("serial")
public class MappingException extends RuntimeException {

	public MappingException(String message) {
		super(message);
	}

	public MappingException(String message, Throwable cause) {
		super(message, cause);
	}
}
