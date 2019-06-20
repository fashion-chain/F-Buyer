package com.hottop.core.model.zpoj.adapter;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class EnumAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<? super T> rawType = typeToken.getRawType();
<<<<<<< HEAD
        return rawType.isEnum() ? new EnumAdapter(rawType) : null;
    }

    public static class EnumAdapter<T extends Enum<T>> extends TypeAdapter<T> {
=======
        if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class) {
            return null;
        }
        if (!rawType.isEnum()) {
            rawType = rawType.getSuperclass(); // handle anonymous subclasses
        }
        return new EnumAdapter(rawType);
    }

    private static class EnumAdapter<T extends Enum<T>> extends TypeAdapter<T> {
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

        private final Class<T> enumClass;

        EnumAdapter(Class<T> enumCls) {
            enumClass = enumCls;
        }

        @Override
        public void write(JsonWriter out, T value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value.name());
            }
        }

        @Override
        public T read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            String stringValue = in.nextString();
            try {
                return Enum.valueOf(enumClass, stringValue);
            } catch (IllegalArgumentException e) {
                throw new JsonParseException(e);
            }
        }
    }
}
<<<<<<< HEAD
=======

>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
