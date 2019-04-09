package com.hottop.core.model.zpoj.adapter.serializer;

import com.google.gson.*;
import com.hottop.core.model.zpoj.cms.IWrapper;
import com.hottop.core.model.zpoj.cms.enums.ETemplateType;

import java.lang.reflect.Type;

public class WrapperSerializer implements JsonSerializer<IWrapper>, JsonDeserializer<IWrapper> {
    @Override
    public IWrapper deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        switch (ETemplateType.valueOf(jsonElement.getAsJsonObject().get("templateType").getAsString())) {
            case component:
                break;
            case widget:
                break;
        }
        throw new JsonParseException("json deserialize error, unknown templateType");
    }

    @Override
    public JsonElement serialize(IWrapper iWrapper, Type type, JsonSerializationContext jsonSerializationContext) {
//        switch () {
//
//        }
        throw new JsonParseException("json serialize error, unknown templateType");
    }
}
