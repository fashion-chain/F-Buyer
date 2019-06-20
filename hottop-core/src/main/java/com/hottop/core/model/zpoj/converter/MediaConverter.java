package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.bean.Media;

import java.lang.reflect.Type;

public class MediaConverter extends GsonAttributeConverter<Media> {
    @Override
    Type getType() {
        return new TypeToken<Media>(){}.getType();
    }
}
