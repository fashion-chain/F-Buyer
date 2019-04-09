package com.hottop.core.model.zpoj.adapter.serializer;

import com.google.gson.*;
import com.hottop.core.model.zpoj.cms.IAction;
import com.hottop.core.model.zpoj.cms.enums.EActionType;

import java.lang.reflect.Type;

public class ActionSerializer implements JsonSerializer<IAction>, JsonDeserializer<IAction> {

    @Override
    public IAction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        switch (EActionType.valueOf(jsonElement.getAsJsonObject().get("actionType").getAsString())) {
            case page_detail:
                break;
            case product_detail:
                break;
        }
        throw new JsonParseException("json deserialize error, unknown actionType");
    }

    @Override
    public JsonElement serialize(IAction action, Type type, JsonSerializationContext jsonSerializationContext) {
        switch (action.getPath()) {
            case page_detail:
                break;
            case product_detail:
                break;
        }
        throw new JsonParseException("json serialize error, unknown actionType");
    }

}
