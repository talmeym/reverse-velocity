package uk.emarte.reverse.velocity;

class MapProcessor {
	private final String insertKey;
	private final ObjectGraphModifier objectModifier;
	
	MapProcessor(String valueLocationToken) {
		if(valueLocationToken.indexOf('.') != -1) {
			String[] tokens = valueLocationToken.split("\\.");
			insertKey = tokens[0];
			String[] modifierTokens = null;

			if(tokens.length > 1) {
				modifierTokens = new String[tokens.length - 1];
				System.arraycopy(tokens, 1, modifierTokens, 0, modifierTokens.length);
			}

			objectModifier = modifierTokens != null ? new ObjectGraphModifier(modifierTokens) : null;
		} else {
			insertKey = valueLocationToken;
			objectModifier = null;
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