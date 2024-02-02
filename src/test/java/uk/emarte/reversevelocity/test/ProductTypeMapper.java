/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.reversevelocity.test;

import uk.emarte.reversevelocity.MappingException;
import uk.emarte.reversevelocity.TypeMapper;

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
