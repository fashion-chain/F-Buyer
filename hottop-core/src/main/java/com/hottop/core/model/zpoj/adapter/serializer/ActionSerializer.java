package com.hottop.core.model.zpoj.adapter.serializer;

import com.google.gson.*;
import com.hottop.core.model.cms.bean.action.ActionBase;
import com.hottop.core.model.cms.bean.action.EActionType;

import java.lang.reflect.Type;

public class ActionSerializer implements JsonDeserializer<ActionBase>, JsonSerializer<ActionBase> {
    @Override
    public ActionBase deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return jsonDeserializationContext.deserialize(jsonElement, EActionType.getCorrespondingType(EActionType.valueOf(jsonElement.getAsJsonObject().get("actionType").getAsString())));
    }

    @Override
    public JsonElement serialize(ActionBase actionBase, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(actionBase, EActionType.getCorrespondingType(actionBase.getActionType()));
    }
}
