package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.model.zpoj.bean.Image;

import java.lang.reflect.Type;

public class ImageConverter extends GsonAttributeConverter<Image> {
    @Override
    Type getType() {
        return new TypeToken<Image>(){}.getType();
    }
}
