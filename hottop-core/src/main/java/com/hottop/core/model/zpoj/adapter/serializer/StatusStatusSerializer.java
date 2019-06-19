package com.hottop.core.model.zpoj.adapter.serializer;

import com.google.gson.*;
import com.hottop.core.feature.status.StatusFactory;
import com.hottop.core.feature.status.StatusStatusTracker;

import java.lang.reflect.Type;

public class StatusStatusSerializer implements JsonSerializer<StatusStatusTracker>, JsonDeserializer<StatusStatusTracker> {
    @Override
    public StatusStatusTracker deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            return StatusFactory.tracker(jsonElement.getAsString());
        } catch (Exception ex) {
            return StatusFactory.dummyTracker;//throw new JsonParseException(ex.getMessage());
        }
    }

    @Override
    public JsonElement serialize(StatusStatusTracker statusStatusTracker, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(statusStatusTracker.status().status(), String.class);
    }
}
