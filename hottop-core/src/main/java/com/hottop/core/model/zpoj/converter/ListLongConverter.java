package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListLongConverter extends GsonAttributeConverter<ArrayList<Long>> {

    @Override
    Type getType() {
        return new TypeToken<ArrayList<Long>>(){}.getType();
    }
}
