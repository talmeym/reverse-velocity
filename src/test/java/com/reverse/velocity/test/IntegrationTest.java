package com.reverse.velocity.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import com.reverse.velocity.ParseException;
import com.reverse.velocity.Parser;
import com.reverse.velocity.ParserFactory;

public class IntegrationTest {
	@Test
	public void testAll() throws IOException, ParseException {
		Parser parser = ParserFactory.createParserFromTemplateStream("testTemplate.xml", getClass().getResourceAsStream("/testTemplate.xml"));
		Map<String, Object> result = parser.parse(IOUtils.toString(getClass().getResourceAsStream("/testMessage.xml")));
		Response response = (Response) result.get("response");
		assertEquals("Test Message", response.getMessage());
		assertEquals(2, response.getProducts().size());
	}
}
