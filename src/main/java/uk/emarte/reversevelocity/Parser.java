/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.reversevelocity;

import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;
import java.util.Map;

public class Parser {
	private final ParserConfig config;

	Parser(ParserConfig config) {
		this.config = config;
	}

	public Map<String, Object> parse(String xml) throws ParseException {
		ListableMap result = new ListableMap();

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			SAXParser parser = factory.newSAXParser();
			parser.parse(new InputSource(new StringReader(xml)), new ParserContentHandler(config, result));
		}
		catch (Exception e) {
			throw new ParseException("Error parsing xml: " + e.getMessage(), e);
		}

		return result;
	}
}
