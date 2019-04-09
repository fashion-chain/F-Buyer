package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.model.zpoj.cms.ITemplate;

import java.lang.reflect.Type;
import java.util.HashMap;

public class MapTemplateConverter extends GsonAttributeConverter<HashMap<String, ITemplate>> {
    @Override
    Type getType() {
        return new TypeToken<HashMap<String, ITemplate>>(){}.getType();
    }
}
