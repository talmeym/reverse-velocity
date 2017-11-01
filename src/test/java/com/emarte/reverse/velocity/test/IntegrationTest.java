package com.emarte.reverse.velocity.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import com.emarte.reverse.velocity.ParseException;
import com.emarte.reverse.velocity.Parser;
import com.emarte.reverse.velocity.ParserFactory;

public class IntegrationTest {
	@Test
	public void testAll() throws IOException, ParseException, java.text.ParseException {
		Parser parser = ParserFactory.createParserFromTemplateStream("testTemplate.xml", streamFile("/testTemplate.xml"));

		Map<String, Object> result = parser.parse(IOUtils.toString(streamFile("/testMessage.xml")));
		Response response = (Response) result.get("response");
		assertEquals("Test Message", response.getMessage());
		assertEquals(parseDate("09-03-1977"), response.getTimestamp());

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
