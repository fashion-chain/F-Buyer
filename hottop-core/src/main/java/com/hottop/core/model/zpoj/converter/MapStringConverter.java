package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class MapStringConverter extends GsonAttributeConverter<HashMap<String, String>>  {
    @Override
    Type getType() {
        return new TypeToken<HashMap<String, String>>(){}.getType();
    }
}
