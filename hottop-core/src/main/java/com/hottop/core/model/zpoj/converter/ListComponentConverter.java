package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.model.zpoj.cms.IComponent;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListComponentConverter extends GsonAttributeConverter<ArrayList<IComponent>> {
    @Override
    Type getType() {
        return new TypeToken<ArrayList<IComponent>>(){}.getType();
    }
}
