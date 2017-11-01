package com.reverse.velocity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TypeResolver {
	@SuppressWarnings("unchecked")
	public static Object resolveType(Class fieldClazz, Object value) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if((fieldClazz == int.class || fieldClazz == Integer.class) && value instanceof String) {
			return Integer.parseInt((String)value);
		} else if((fieldClazz == float.class || fieldClazz == Float.class) && value instanceof String) {
			return Float.parseFloat((String)value);
		} else if((fieldClazz == float.class || fieldClazz == Float.class) && value instanceof String) {
			return Float.parseFloat((String)value);
		} else if((fieldClazz == double.class || fieldClazz == Double.class) && value instanceof String) {
			return Double.parseDouble((String)value);
		} else if((fieldClazz == boolean.class || fieldClazz == Boolean.class) && value instanceof String) {
			return Boolean.valueOf((String) value);
		} else if(fieldClazz.isEnum() && value instanceof String) {
			Method valueOfMethod = fieldClazz.getMethod("valueOf", String.class);
			return valueOfMethod.invoke(null, value);
		}
		return value;
	}
}
