package uk.emarte.reverse.velocity.test;

import uk.emarte.reverse.velocity.MappingException;
import uk.emarte.reverse.velocity.TypeMapper;

public class ProductTypeMapper implements TypeMapper {
	@Override
	public Object map(String value) throws MappingException {
		try {
			return ProductType.valueOf(value);
		} catch(IllegalArgumentException e) {
			throw new MappingException("Could not find product type: " + value);
		}
	}
}
