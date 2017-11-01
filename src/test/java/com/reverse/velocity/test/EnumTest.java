package com.reverse.velocity.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnumTest {
	public static final void main(String[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class clazz = ProductType.class;
		Method method = clazz.getMethod("valueOf", String.class);
		ProductType productType = (ProductType) method.invoke(null, "somethingInvalid");
		System.out.println(productType);
	}
}
