package uk.emarte.reverse.velocity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TypeResolver {
	@SuppressWarnings({"JavaReflectionInvocation"})
	public static Object resolveType(Class<?> fieldClazz, Object value) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if(value instanceof String) {
			if(fieldClazz == String.class) {
				return value;
			} if (fieldClazz == int.class || fieldClazz == Integer.class) {
				return Integer.parseInt((String) value);
			} else if (fieldClazz == long.class || fieldClazz == Long.class) {
				return Long.parseLong((String) value);
			} else if (fieldClazz == float.class || fieldClazz == Float.class) {
				return Float.parseFloat((String) value);
			} else if (fieldClazz == double.class || fieldClazz == Double.class) {
				return Double.parseDouble((String) value);
			} else if (fieldClazz == boolean.class || fieldClazz == Boolean.class) {
				return Boolean.valueOf((String) value);
			} else if (fieldClazz.isEnum()) {
				Method valueOfMethod = fieldClazz.getMethod("valueOf", String.class);
				return valueOfMethod.invoke(null, value);
			}
		}

		// TODO overcome auto-boxing limitation ??
		if(value instanceof Integer && (fieldClazz == int.class)) {
			return value;
		}

		// TODO overcome auto-boxing limitation ??
		if(value instanceof Float && (fieldClazz == float.class)) {
			return value;
		}

		throw new IllegalArgumentException(String.format("Cannot resolve type from fieldClass '%s' and value '%s'", fieldClazz.getName(), value));
	}
}
