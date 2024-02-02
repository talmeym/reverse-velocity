package uk.emarte.reverse.velocity.mapping;

import uk.emarte.reverse.velocity.MappingException;
import uk.emarte.reverse.velocity.TypeMapper;

public class JavaTypesMapper implements TypeMapper {
    private Type type;

    public JavaTypesMapper(String type) {
        if(!Type.contains(type)) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }

        this.type = Type.valueOf(type);
    }

    @Override
    public Object map(String value) throws MappingException {
        return type.parseToType(value);
    }

    enum Type {
        INT {
            @Override
            Object parseToType(String value) {
                return Integer.parseInt(value);
            }
        },
        LONG {
            @Override
            Object parseToType(String value) {
                return Long.parseLong(value);
            }
        },
        FLOAT {
            @Override
            Object parseToType(String value) {
                return Float.parseFloat(value);
            }
        },
        DOUBLE {
            @Override
            Object parseToType(String value) {
                return Double.parseDouble(value);
            }
        };

        abstract Object parseToType(String value);

        static boolean contains(String string) {
            Type[] types = values();

            for(Type type: types) {
                if(type.name().equals(string)) {
                    return true;
                }
            }

            return false;
        }
    }
}
