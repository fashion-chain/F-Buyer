package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.model.zpoj.bean.Media;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListMediaConverter extends GsonAttributeConverter<ArrayList<Media>> {

    @Override
    Type getType() {
        return new TypeToken<ArrayList<Media>>(){}.getType();
    }
}
