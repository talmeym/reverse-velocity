package com.emarte.reverse.velocity;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import com.emarte.reverse.velocity.test.ProductType;

public class TypeResolverTest {
    @Test
    public void testThis() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        assertEquals(12.5f, TypeResolver.resolveType(float.class, "12.5"));
        assertEquals(12.5f, TypeResolver.resolveType(Float.class, "12.5"));

        assertEquals(12.5d, TypeResolver.resolveType(double.class, "12.5"));
        assertEquals(12.5d, TypeResolver.resolveType(Double.class, "12.5"));

        assertEquals(Boolean.TRUE, TypeResolver.resolveType(boolean.class, "true"));
        assertEquals(Boolean.TRUE, TypeResolver.resolveType(Boolean.class, "true"));
        assertEquals(Boolean.FALSE, TypeResolver.resolveType(boolean.class, "false"));
        assertEquals(Boolean.FALSE, TypeResolver.resolveType(Boolean.class, "false"));

        assertEquals(ProductType.installation, TypeResolver.resolveType(ProductType.class, "installation"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonStringArgumentExceptions() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        assertEquals(12.5d, TypeResolver.resolveType(Double.class, 12.5d));
    }
}