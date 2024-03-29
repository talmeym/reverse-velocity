/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.reversevelocity;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class ObjectGraphModifier {
	private final String[] fieldNames;

	ObjectGraphModifier(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}

	@SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
	void modifyField(final Object obj, final Object value) {
		Object target = obj;
		Class<?> targetClass = obj.getClass();

		try {
			for (int i = 0; i < fieldNames.length; i++) {
				String fieldName = fieldNames[i];
				Method getter = targetClass.getMethod(getterMethodName(fieldName));
				Object fieldValue = getter.invoke(target);

				if (i < (fieldNames.length - 1)) {
					if (fieldValue instanceof Object[]) {
						Object[] array = (Object[]) fieldValue;
						target = array[array.length - 1];
						targetClass = fieldValue.getClass().getComponentType();
					} else if (fieldValue instanceof List) {
						List<Object> list = (List<Object>) fieldValue;
						Object lastElement = list.get(list.size() - 1);
						target = lastElement;
						targetClass = lastElement.getClass();
					} else {
						target = fieldValue;
						targetClass = fieldValue.getClass();
					}
				} else {
					Class<?> fieldClazz = getter.getReturnType();
					Class<?> componentClass = fieldClazz.getComponentType();
					Method setter = targetClass.getMethod(setterMethodName(fieldName), fieldClazz);

					if (componentClass != null) {
						if (fieldValue != null) {
							Object[] array = (Object[]) fieldValue;
							Object newArray = Array.newInstance(componentClass, array.length + 1);
							System.arraycopy(array, 0, newArray, 0, array.length);
							Array.set(newArray, array.length, value);
							setter.invoke(target, newArray);
						} else {
							Object array = Array.newInstance(componentClass, 1);
							Array.set(array, 0, value);
							setter.invoke(target, array);
						}
					} else if (fieldClazz == List.class) {
						if (fieldValue != null) {
							List<Object> list = (List<Object>) fieldValue;
							list.add(value);
						} else {
							List<Object> newList = new ArrayList<>();
							newList.add(value);
							setter.invoke(target, newList);
						}
					} else {
						Object setterArg = value;

						if(!setterArg.getClass().isAssignableFrom(fieldClazz)) {
							setterArg = TypeResolver.resolveType(fieldClazz, setterArg);
						}

						setter.invoke(target, setterArg);
					}
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
			throw new IllegalStateException(
					"Could not modify '" + String.join(".", fieldNames) +
					"' of object graph '" + obj + "' with value of class '" +
					value.getClass().getName() + "'. Check cause exception for more information.", e);
		}
	}

	private String getterMethodName(String fieldName) {
		StringBuilder methodName = new StringBuilder(fieldName);
		methodName.setCharAt(0, String.valueOf(methodName.charAt(0)).toUpperCase().charAt(0));
		methodName.insert(0, "get");
		return methodName.toString();
	}

	private String setterMethodName(String fieldName) {
		StringBuilder methodName = new StringBuilder(fieldName);
		methodName.setCharAt(0, String.valueOf(methodName.charAt(0)).toUpperCase().charAt(0));
		methodName.insert(0, "set");
		return methodName.toString();
	}

	protected String[] getFieldNames() {
		return fieldNames;
	}
}
