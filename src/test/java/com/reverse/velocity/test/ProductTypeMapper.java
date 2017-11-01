package com.reverse.velocity.test;

import com.reverse.velocity.MappingException;
import com.reverse.velocity.TypeMapper;

public class ProductTypeMapper implements TypeMapper {
	@Override
	public Object map(String value) throws MappingException {
		ProductType productType = ProductType.valueOf(value);
		
		if(productType == null) {
			throw new MappingException("Could not find product type: " + value);
		}
		
		return productType;
	}
}
