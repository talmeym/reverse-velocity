package uk.emarte.reversevelocity;

public interface TypeMapper {
	Object map(String value) throws MappingException;
}
