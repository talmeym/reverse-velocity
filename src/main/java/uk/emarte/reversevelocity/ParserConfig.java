/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.reversevelocity;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

class ParserConfig {
	private final Map<String, MapProcessor> elementProcessorsByPath = new HashMap<>();
	private final Map<String, MapProcessor> textProcessorsByPath = new HashMap<>();
	private final Map<String, Class<?>> classesByPath = new HashMap<>();
	private final Map<String, Boolean> listByPath = new HashMap<>();
	private final Map<String, TypeMapper> typeMappersByPath = new HashMap<>();

	void addProcessorForElement(String path, MapProcessor processor) {
		checkKey(path, elementProcessorsByPath, "element processing");
		elementProcessorsByPath.put(path, processor);
	}
	
	MapProcessor getProcessorForElement(String path) {
		return elementProcessorsByPath.get(path);
	}
	
	void addProcessorForText(String path, MapProcessor processor) {
		checkKey(path, textProcessorsByPath, "text processing");
		textProcessorsByPath.put(path, processor);
	}
	
	MapProcessor getProcessorForText(String path) {
		return textProcessorsByPath.get(path);
	}
	
	void addClassForPath(String path, Class<?> clazz) {
		checkKey(path, classesByPath, "class definition");
		classesByPath.put(path, clazz);
	}
	
	Class<?> getClassForPath(String path) {
		return classesByPath.get(path);
	}
	
	void addListForPath(String path, Boolean list) {
		checkKey(path, listByPath, "list definition");
		listByPath.put(path, list);
	}
	
	Boolean getListForPath(String path) {
		return listByPath.get(path);
	}
	
	void addTypeMapperForPath(String path, TypeMapper mapper) {
		checkKey(path, typeMappersByPath, "type mapper definition");
		typeMappersByPath.put(path, mapper);
	}
	
	TypeMapper getTypeMapperForPath(String path) {
		return typeMappersByPath.get(path);
	}

	private void checkKey(String path, Map<?, ?> map, String context) {
		if(map.containsKey(path)) {
			throw new IllegalStateException("Duplicate processing instruction, context='" + context + "', path='" + path + "'");
		}
	}

	void validate() {
		for (Map.Entry<String, MapProcessor> entry : elementProcessorsByPath.entrySet()) {
			MapProcessor mapProcessor = entry.getValue();

			if (mapProcessor.getFieldModifier() != null) {
				if (!classesByPath.containsKey(entry.getKey())) {
					throw new IllegalStateException("class definition missing for element insert [" + mapProcessor + "]");
				}

				Class<?> clazz = null;

				try {
					clazz = classesByPath.get(entry.getKey());
					clazz.getDeclaredConstructor().newInstance();
				} catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex) {
					throw new IllegalStateException("class [" + clazz.getName() + "] cannot be instantiated");
				}

				if (listByPath.containsKey(entry.getKey())) {
					throw new IllegalStateException("Invalid list attribute at non-top-level insert[path=" + entry.getKey() + "]");
				}
			}
		}
	}
}
