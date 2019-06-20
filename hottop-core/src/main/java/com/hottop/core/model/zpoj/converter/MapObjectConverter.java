package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class MapObjectConverter extends GsonAttributeConverter<HashMap<String, Object>>  {
    @Override
    Type getType() {
        return new TypeToken<HashMap<String, Object>>(){}.getType();
    }
}
