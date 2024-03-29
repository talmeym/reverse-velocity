/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.reversevelocity.test;

import org.junit.Test;
import uk.emarte.reversevelocity.FileUtil;
import uk.emarte.reversevelocity.ParseException;
import uk.emarte.reversevelocity.Parser;
import uk.emarte.reversevelocity.ParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class IntegrationTest {
	@Test
	public void testXmlNoInserts() throws IOException, ParseException, java.text.ParseException {
		Parser parser = ParserFactory.createParserFromTemplateStream(null, streamFile("/testTemplateNoInserts.xml"));
		Map<String, Object> result = parser.parse(FileUtil.toString(streamFile("/testMessage.xml")));

		assertEquals("Test Message", result.get("response-message"));
		assertEquals(parseDate("08-06-1987"), result.get("response-timestamp"));
		assertEquals(asList("installation", "hardware"), result.get("response-product-types"));
		assertEquals(asList(1, 2), result.get("response-product-ids"));
		assertEquals(asList("STB Standard Install", "STB HD"), result.get("response-product-names"));
		assertEquals(asList("Install for a STB Standard", "An STB HD box"), result.get("response-product-descriptions"));
	}

	@Test
	public void testXmlWithInserts() throws IOException, ParseException, java.text.ParseException {
		Parser parser = ParserFactory.createParserFromTemplateStream(null, streamFile("/testTemplateInserts.xml"));
		Map<String, Object> result = parser.parse(FileUtil.toString(streamFile("/testMessage.xml")));

		assertResultsObjects(result);
	}

	@Test
	public void testNameSpacedXml() throws IOException, ParseException, java.text.ParseException {
		Parser parser = ParserFactory.createParserFromTemplateStream(null, streamFile("/namespacedTestTemplate.xml"));
		Map<String, Object> result = parser.parse(FileUtil.toString(streamFile("/namespacedTestMessage.xml")));

		assertResultsObjects(result);
	}

	private void assertResultsObjects(Map<String, Object> result) throws java.text.ParseException {
		Response response = (Response) result.get("response");
		assertEquals("Test Message", response.getMessage());
		assertEquals(parseDate("08-06-1987"), response.getTimestamp());

		List<Product> products = response.getProducts();
		assertEquals(2, products.size());

		assertProduct(products.get(0), 1, "STB Standard Install", "Install for a STB Standard", ProductType.installation);
		assertProduct(products.get(1), 2, "STB HD", "An STB HD box", ProductType.hardware);
	}

	private InputStream streamFile(String filename) {
		return getClass().getResourceAsStream(filename);
	}

	private Date parseDate(String date) throws java.text.ParseException {
		return new SimpleDateFormat("dd-MM-yyyy").parse(date);
	}

	private void assertProduct(Product product, int id, String name, String description, ProductType productType) {
		assertEquals(id, product.getId());
		assertEquals(name, product.getName());
		assertEquals(description, product.getDescription());
		assertEquals(productType, product.getType());
	}
}
