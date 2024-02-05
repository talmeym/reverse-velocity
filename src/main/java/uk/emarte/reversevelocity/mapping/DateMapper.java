/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.reversevelocity.mapping;

import uk.emarte.reversevelocity.MappingException;
import uk.emarte.reversevelocity.TypeMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateMapper implements TypeMapper {
	private final String dateFormat;
	
	public DateMapper(String arg) {
		dateFormat = arg;
	}
	
	public Object map(String value) throws MappingException {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		try {
			return sdf.parse(value);
		} catch (ParseException pe) {
			throw new MappingException("Error mapping date: " + pe.getMessage(), pe);
		}
	}
}
