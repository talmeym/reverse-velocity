package com.reverse.velocity;

import java.io.StringReader;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

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
		catch (Exception ex) {
			throw new ParseException("Error parsing xml: " + ex.getMessage(), ex);
		}

		return result;
	}
}
