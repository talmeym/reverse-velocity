package com.emarte.reverse.velocity.mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.emarte.reverse.velocity.MappingException;
import com.emarte.reverse.velocity.TypeMapper;

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
