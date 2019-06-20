package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.model.community.bean.FeedEntity;

import java.lang.reflect.Type;

public class FeedEntityConverter extends GsonAttributeConverter<FeedEntity> {
    @Override
    Type getType() {
        return new TypeToken<FeedEntity>(){}.getType();
    }
}
