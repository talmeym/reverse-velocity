/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.reversevelocity;

import org.xml.sax.Attributes;

import java.lang.reflect.InvocationTargetException;

class ParserContentHandler extends AbstractContentHandler {
	private final ParserConfig config;
	private final ListableMap result;

	ParserContentHandler(ParserConfig config, ListableMap result) {
		this.config = config;
		this.result = result;
	}

	@Override
	protected void processElement(String path, Attributes attributes) {
		MapProcessor valueProcessor = config.getProcessorForElement(path);
		Class<?> clazz = null;
		Boolean list;

		if (valueProcessor != null) {
			try {
				clazz = config.getClassForPath(path);
				list = config.getListForPath(path);
				valueProcessor.processValue(result, clazz.getDeclaredConstructor().newInstance(), list != null && list);
			}
			catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ie) {
				throw new IllegalStateException("Failed to instantiate class [" + clazz + "]");
			}
		}

		if (attributes.getLength() > 0) {
			processAttributes(path, attributes);
		}
	}

	@Override
	protected void processText(String path, String text) {
		process(path, text);
	}

	private void processAttributes(String path, Attributes attributes) {
		for (int i = 0; i < attributes.getLength(); i++) {
			String pathToCheck = path + "@" + attributes.getLocalName(i);
			process(pathToCheck, attributes.getValue(i));
		}
	}
	
	private void process(String path, String text) {
		MapProcessor valueProcessor = config.getProcessorForText(path);

		if (valueProcessor != null) {
			Object value = text;
			TypeMapper mapper = config.getTypeMapperForPath(path);

			if (mapper != null) {
				value = mapper.map(text);
			}

			valueProcessor.processValue(result, value, false);
		}
	}
}
