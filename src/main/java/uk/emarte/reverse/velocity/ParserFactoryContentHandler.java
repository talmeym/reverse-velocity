package uk.emarte.reverse.velocity;

import org.xml.sax.Attributes;

class ParserFactoryContentHandler extends AbstractContentHandler {
	private ParserConfig config;
	
	ParserFactoryContentHandler(ParserConfig config) {
		this.config = config;
	}

	@Override
	protected void processElement(String path, Attributes attributes) {
		String insert = attributes.getValue("_insert");
		String clazz = attributes.getValue("_class");
		String list = attributes.getValue("_list");

		if (insert != null && clazz != null) {
			try {
				config.addProcessorForElement(path, new MapProcessor(insert));
				config.addClassForPath(path, Class.forName(clazz));

				if (list != null) {
					config.addListForPath(path, list.equalsIgnoreCase("true"));
				}
			}
			catch (ClassNotFoundException e) {
				throw new IllegalArgumentException("Class [" + clazz + "] not found");
			}
		}

		addTypeMapperIfAppropriate(path, attributes.getValue("_type"), attributes.getValue("_mapper"));
		
		if (attributes.getLength() > 0) {
			processAttributes(path, attributes);
		}
	}

	private void addTypeMapperIfAppropriate(String path, String type, String mapper) {
		if(type != null) {
			mapper = "uk.emarte.reverse.velocity.mapping.JavaTypesMapper[" + type + "]";
		}
		if(mapper != null) {
			TypeMapperLoader.loadTypeMapper(path, mapper, config);
		}
	}

	@Override
	protected void processText(String path, String text) {
		if (text.startsWith("$")) {
			config.addProcessorForText(path, new MapProcessor(text.substring(1)));
		}
	}

	private void processAttributes(String path, Attributes attributes) {
		for (int i = 0; i < attributes.getLength(); i++) {
			String attributeValue = attributes.getValue(i);

			if (attributeValue.startsWith("$")) {
				String pathToInsert = path + "@" + attributes.getLocalName(i);
				config.addProcessorForText(pathToInsert, new MapProcessor(attributeValue.substring(1)));
			}
		}
	}
}
