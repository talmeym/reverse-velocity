package uk.emarte.reversevelocity;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

public class ParserFactory {
	public static Parser createParserFromTemplateFile(File templateFile) throws IOException {
		return createParserFromTemplateStream(templateFile.getName(), new FileInputStream(templateFile));
	}
	
	public static Parser createParserFromTemplateStream(String templateName, InputStream templateStream) {
		ParserConfig config = new ParserConfig();
		
		try {
			String template = FileUtil.toString(templateStream);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			SAXParser parser = factory.newSAXParser();
			parser.parse(new InputSource(new StringReader(template)), new ParserFactoryContentHandler(config));
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Template '" + templateName + "' could not be read");
		} catch (SAXException e) {
			throw new IllegalArgumentException("Template '" + templateName + "' contains invalid xml");
		} catch (ParserConfigurationException e) {
			throw new IllegalArgumentException("Cannot create SAXParser - check internal config");
		}

		config.validate();
		return new Parser(config);
	}
}
