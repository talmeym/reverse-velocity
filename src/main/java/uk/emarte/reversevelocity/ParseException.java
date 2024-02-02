package uk.emarte.reversevelocity;

@SuppressWarnings("serial")
public class ParseException extends Exception {
	public ParseException(String string, Exception ex) {
		super(string, ex);
	}
}
