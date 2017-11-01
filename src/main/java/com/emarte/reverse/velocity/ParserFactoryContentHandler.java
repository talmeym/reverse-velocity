package com.emarte.reverse.velocity;

import org.xml.sax.Attributes;

class ParserFactoryContentHandler extends AbstractContentHandler {
	private ParserConfig config;
	
	ParserFactoryContentHandler(ParserConfig config) {
		this.config = config;
	}

	@Override
	protected void processElement(String path, Attributes attributes) {
		String insert = attributes.getValue("insert");
		String clazz = attributes.getValue("class");
		String list = attributes.getValue("list");

		if (insert != null && clazz != null) {
			try {
				config.addProcessorForElement(path, new MapProcessor(insert));
				config.addClassForPath(path, Class.forName(clazz));
				if (list != null) {
					config.addListForPath(path, list.equalsIgnoreCase("true"));
				}
			}
			catch (ClassNotFoundException e) {
				throw new IllegalStateException("Class [" + clazz + "] not found");
			}
		}

		addTypeMapperIfAppropriate(path, attributes.getValue("mapper"));
		
		if (attributes.getLength() > 0) {
			processAttributes(path, attributes);
		}
	}

	private void addTypeMapperIfAppropriate(String path, String mapper) {
		if(mapper != null) {
			TypeMapperLoader.loadTypeMapper(path, mapper, config);
		}
	}

	@Override
	protected void processText(String path, String text) {
		if (text.startsWith("$")) {
			config.addProcessorForText(path, new MapProcessor(text));
		}
	}

	private void processAttributes(String path, Attributes attributes) {
		for (int i = 0; i < attributes.getLength(); i++) {
			String attributeValue = attributes.getValue(i);
			if (attributeValue.startsWith("$")) {
				String pathToInsert = path + "@" + attributes.getLocalName(i);
				config.addProcessorForText(pathToInsert, new MapProcessor(attributeValue));
			}
		}
	}
}
