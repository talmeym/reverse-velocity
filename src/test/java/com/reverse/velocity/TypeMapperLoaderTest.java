package com.reverse.velocity;

import org.junit.Test;

public class TypeMapperLoaderTest {
    @Test
    public void testThis() {
        TypeMapperLoader.loadTypeMapper("/response/product", "@type:com.reverse.velocity.test.ProductTypeMapper", new ParserConfig());
        TypeMapperLoader.loadTypeMapper("/response/product", "com.reverse.velocity.mapping.DateMapper[HH:mm]", new ParserConfig());
    }
}