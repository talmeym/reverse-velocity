package uk.emarte.reverse.velocity;

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
