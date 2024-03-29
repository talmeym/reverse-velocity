/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.reversevelocity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class TypeMapperLoader {
	static void loadTypeMapper(String path, String mapper, ParserConfig config) {
		String attribute = "";
		String arg = null;
		int index = mapper.indexOf("[");

		if(index != -1) {
			arg = mapper.substring(mapper.indexOf("[") + 1, mapper.indexOf("]"));
			mapper = mapper.substring(0, mapper.indexOf("["));
		}

		index = mapper.indexOf(':');

		if (index != -1) {
			attribute = mapper.substring(0, mapper.indexOf(":"));
			mapper = mapper.substring(mapper.indexOf(":") + 1);
		}

		try {
			Class<?> mapperClass = Class.forName(mapper);
			Object mapperObj;

			if(arg != null) {
				Constructor<?> constructor = mapperClass.getConstructor(String.class);
				mapperObj = constructor.newInstance(arg);
			} else {
				mapperObj = mapperClass.getDeclaredConstructor().newInstance();
			}

			if (mapperObj instanceof TypeMapper) {
				config.addTypeMapperForPath(path + attribute, (TypeMapper) mapperObj);
			}
		}
		catch (ClassNotFoundException e) {
			throw new IllegalStateException("Class [" + mapper + "] not found", e);
		}
		catch (NoSuchMethodException e) {
			throw new IllegalStateException("Constructor for [" + mapper + "] not found", e);
		}
		catch (InvocationTargetException e) {
			throw new IllegalStateException("Exception thrown invoking constructor for [" + mapper + "] with [" + arg + "]", e);
		}
		catch (InstantiationException e) {
			throw new IllegalStateException("Class [" + mapper + "] not instantiated", e);
		}
		catch (IllegalAccessException e) {
			throw new IllegalStateException("Class [" + mapper + "] not accessible", e);
		}
	}
}
