package uk.emarte.reverse.velocity;

public interface TypeMapper {
	Object map(String value) throws MappingException;
}
