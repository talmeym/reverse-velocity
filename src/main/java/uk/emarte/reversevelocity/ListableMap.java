package uk.emarte.reversevelocity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("serial")
class ListableMap extends HashMap<String, Object> {
	public Object putListable(String key, Object value) {
		return putListable(key, value, false);
	}
	
	@SuppressWarnings("unchecked")
	Object putListable(String key, Object value, boolean forceList) {
		Object existing = get(key);

		if(existing instanceof List) {
			List<Object> list = (List<Object>) existing;
			list.add(value);
			return existing;
		}

		if(existing != null || forceList) {
			List<Object> list = new ArrayList<>();

			if(existing != null) {
				list.add(existing);
			}
			
			list.add(value);
			put(key, list);
			return list;
		}

		return put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	Object getListable(String key) {
		Object existing = get(key);

		if(existing instanceof List) {
			List<Object> list = (List<Object>) existing;
			return list.get(list.size() - 1);
		}

		return get(key);
	}
}