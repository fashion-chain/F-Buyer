package com.hottop.core.model.zpoj.adapter.serializer;

import com.google.gson.*;
import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.EComponentType;

import java.lang.reflect.Type;

public class ComponentSerializer implements JsonDeserializer<ComponentBase>, JsonSerializer<ComponentBase> {
    @Override
    public ComponentBase deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return jsonDeserializationContext.deserialize(jsonElement, EComponentType.getCorrespondingType(EComponentType.valueOf(jsonElement.getAsJsonObject().get("componentType").getAsString())));
    }

    @Override
    public JsonElement serialize(ComponentBase componentBase, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(componentBase, EComponentType.getCorrespondingType(componentBase.getComponentType()));
    }
}
