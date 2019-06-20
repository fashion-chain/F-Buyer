package com.hottop.core.model.zpoj.adapter.serializer;

import com.google.gson.*;
<<<<<<< HEAD
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

=======
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
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
}
