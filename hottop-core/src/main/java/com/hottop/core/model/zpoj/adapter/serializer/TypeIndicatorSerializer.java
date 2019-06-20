package com.hottop.core.model.zpoj.adapter.serializer;

import com.google.gson.*;
import com.hottop.core.feature.type.TypeFactory;
import com.hottop.core.feature.type.TypeIndicator;
import com.hottop.core.utils.LogUtil;

import java.lang.reflect.Type;

public class TypeIndicatorSerializer implements JsonSerializer<TypeIndicator>, JsonDeserializer<TypeIndicator> {
    @Override
    public TypeIndicator deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            return TypeFactory.indicator(jsonElement.getAsString());
        } catch (Exception ex) {
            LogUtil.error(String.format("error in typeIndicatorSerializer.deserialize message: %s", ex.getMessage()));
            return TypeFactory.dummyIndicator;
        }
    }

    @Override
    public JsonElement serialize(TypeIndicator typeIndicator, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(typeIndicator.name(), String.class);
    }
}
