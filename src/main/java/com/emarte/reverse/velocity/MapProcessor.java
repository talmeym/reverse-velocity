package com.emarte.reverse.velocity;

class MapProcessor {
	private String insertKey;
	private ObjectGraphModifier objectModifier;
	
	MapProcessor(String valueLocationToken) {
		if(valueLocationToken.indexOf('.') != -1) {
			String[] tokens = valueLocationToken.split("\\.");
			insertKey = tokens[0];

			if(tokens.length > 1) {
				String[] modifierTokens = new String[tokens.length - 1];
				System.arraycopy(tokens, 1, modifierTokens, 0, modifierTokens.length);
				objectModifier = new ObjectGraphModifier(modifierTokens);
			}
		} else {
			insertKey = valueLocationToken;
		}
	}
	
	void processValue(ListableMap target, Object value, boolean list) {
		if(objectModifier != null) {
			Object toModify = target.getListable(insertKey);
			
			if(toModify == null) {
				throw new IllegalStateException("Object [key=" + insertKey + "] does not exist in the target map");
			}
			
			objectModifier.modifyField(toModify, value);
		} else {
			target.putListable(insertKey, value, list);
		}
	}

	protected ObjectGraphModifier getFieldModifier() {
		return objectModifier;
	}
}