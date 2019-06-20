package com.hottop.core.model.zpoj.adapter.serializer;

import com.google.gson.*;
import com.hottop.core.model.cms.bean.component.EComponentType;
import com.hottop.core.model.cms.bean.component.bean.ComponentDecorator;
import com.hottop.core.model.cms.bean.component.bean.EComponentDecoratorType;

import java.lang.reflect.Type;

public class ComponentDecoratorSerializer implements JsonDeserializer<ComponentDecorator>, JsonSerializer<ComponentDecorator> {
    @Override
    public ComponentDecorator deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return jsonDeserializationContext.deserialize(jsonElement, EComponentDecoratorType.getCorrespondingType(EComponentDecoratorType.valueOf(jsonElement.getAsJsonObject().get("decoratorType").getAsString())));
    }

    @Override
    public JsonElement serialize(ComponentDecorator decorator, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(decorator, EComponentDecoratorType.getCorrespondingType(decorator.getDecoratorType()));
    }
}
