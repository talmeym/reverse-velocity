package uk.emarte.reverse.velocity;

import org.junit.Test;

public class TypeMapperLoaderTest {
    @Test
    public void testThis() {
        TypeMapperLoader.loadTypeMapper("/response/product", "@type:uk.emarte.reverse.velocity.test.ProductTypeMapper", new ParserConfig());
        TypeMapperLoader.loadTypeMapper("/response/product", "uk.emarte.reverse.velocity.mapping.DateMapper[HH:mm]", new ParserConfig());
    }
}