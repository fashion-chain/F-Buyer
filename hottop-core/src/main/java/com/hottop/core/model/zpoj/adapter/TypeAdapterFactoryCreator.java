package com.hottop.core.model.zpoj.adapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class TypeAdapterFactoryCreator {

    public static <X> TypeAdapterFactory newFactory(final Class<X> boxed,
                                                     final TypeAdapter<? super X> typeAdapter) {
        return new TypeAdapterFactory() {
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                Class<? super T> rawType = typeToken.getRawType();
                return (rawType == boxed) ? (TypeAdapter<T>) typeAdapter : null;
            }
        };
    }
}
