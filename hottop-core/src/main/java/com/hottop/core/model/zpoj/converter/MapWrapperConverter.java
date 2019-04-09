package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.model.zpoj.cms.IWrapper;

import java.lang.reflect.Type;
import java.util.HashMap;

public class MapWrapperConverter extends GsonAttributeConverter<HashMap<String, HashMap<String, IWrapper>>> {
    @Override
    Type getType() {
        return new TypeToken<HashMap<String, HashMap<String, IWrapper>>>(){}.getType();
    }
}
