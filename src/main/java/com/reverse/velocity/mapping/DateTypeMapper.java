package com.reverse.velocity.mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.reverse.velocity.MappingException;
import com.reverse.velocity.TypeMapper;

public class DateTypeMapper implements TypeMapper {
	private final String dateFormat;
	
	public DateTypeMapper(String arg) {
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
