package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.model.zpoj.bean.Image;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListImageConverter extends GsonAttributeConverter<ArrayList<Image>> {

    @Override
    Type getType() {
        return new TypeToken<ArrayList<Image>>(){}.getType();
    }
}
