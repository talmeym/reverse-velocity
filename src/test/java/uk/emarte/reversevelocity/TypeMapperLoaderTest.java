/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.reversevelocity;

import org.junit.Test;

public class TypeMapperLoaderTest {
    @Test
    public void testThis() {
        TypeMapperLoader.loadTypeMapper("/response/product", "@type:uk.emarte.reversevelocity.test.ProductTypeMapper", new ParserConfig());
        TypeMapperLoader.loadTypeMapper("/response/product", "uk.emarte.reversevelocity.mapping.DateMapper[HH:mm]", new ParserConfig());
    }
}