package com.emarte.reverse.velocity.test;

import com.emarte.reverse.velocity.MappingException;
import com.emarte.reverse.velocity.TypeMapper;

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
