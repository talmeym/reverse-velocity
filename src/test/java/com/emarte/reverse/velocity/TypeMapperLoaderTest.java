package com.emarte.reverse.velocity;

import org.junit.Test;

public class TypeMapperLoaderTest {
    @Test
    public void testThis() {
        TypeMapperLoader.loadTypeMapper("/response/product", "@type:com.emarte.reverse.velocity.test.ProductTypeMapper", new ParserConfig());
        TypeMapperLoader.loadTypeMapper("/response/product", "com.emarte.reverse.velocity.mapping.DateMapper[HH:mm]", new ParserConfig());
    }
}