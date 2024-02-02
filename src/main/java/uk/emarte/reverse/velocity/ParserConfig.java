package uk.emarte.reverse.velocity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("unchecked")
class ParserConfig {
	private final Map<String, MapProcessor> elementProcessorsByPath = new HashMap<String, MapProcessor>();
	private final Map<String, MapProcessor> textProcessorsByPath = new HashMap<String, MapProcessor>();
	private final Map<String, Class> classesByPath = new HashMap<String, Class>();
	private final Map<String, Boolean> listByPath = new HashMap<String, Boolean>();
	private final Map<String, TypeMapper> typeMappersByPath = new HashMap<String, TypeMapper>();

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
	
	void addClassForPath(String path, Class clazz) {
		checkKey(path, classesByPath, "class definition");
		classesByPath.put(path, clazz);
	}
	
	Class getClassForPath(String path) {
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

	private void checkKey(String path, Map map, String context) {
		if(map.containsKey(path)) {
			throw new IllegalStateException("Duplicate processing instruction, context='" + context + "', path='" + path + "'");
		}
	}

	void validate() {
		for (Iterator<Map.Entry<String, MapProcessor>> iterator = elementProcessorsByPath.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, MapProcessor> entry = iterator.next();
			MapProcessor mapProcessor = entry.getValue();

			if (mapProcessor.getFieldModifier() != null) {
				if (!classesByPath.keySet().contains(entry.getKey())) {
					throw new IllegalStateException("class definition missing for element insert [" + mapProcessor + "]");
				}

				Class clazz = null;

				try {
					clazz = classesByPath.get(entry.getKey());
					clazz.newInstance();
				}
				catch (IllegalAccessException ex) {
					throw new IllegalStateException("class [" + clazz.getName() + "] cannot be instantiated");
				}
				catch (InstantiationException e) {
					throw new IllegalStateException("class [" + clazz.getName() + "] cannot be instantiated");
				}

				if (listByPath.containsKey(entry.getKey())) {
					throw new IllegalStateException("Invalid list attribute at non-top-level insert[path=" + entry.getKey() + "]");
				}
			}
		}
	}
}
