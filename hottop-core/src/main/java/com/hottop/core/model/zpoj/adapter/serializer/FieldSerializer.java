package com.hottop.core.model.zpoj.adapter.serializer;

import com.google.gson.*;
import com.hottop.core.model.zpoj.cms.enums.EFieldType;
import com.hottop.core.model.zpoj.cms.field.*;

import java.lang.reflect.Type;

public class FieldSerializer implements JsonSerializer<FieldBase>, JsonDeserializer<FieldBase> {
    @Override
    public FieldBase deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        switch (EFieldType.valueOf(jsonElement.getAsJsonObject().get("fieldTemplate").getAsJsonObject().get("fieldType").getAsString())) {
            case field_string:
                return jsonDeserializationContext.deserialize(jsonElement, FieldString.class);
            case field_double:
                return jsonDeserializationContext.deserialize(jsonElement, FieldDouble.class);
            case field_long:
                return jsonDeserializationContext.deserialize(jsonElement, FieldLong.class);
            case field_date:
                return jsonDeserializationContext.deserialize(jsonElement, FieldDate.class);
        }
        throw new JsonParseException("json deserialize error, unknown fieldType");
    }

    @Override
    public JsonElement serialize(FieldBase fieldBase, Type type, JsonSerializationContext jsonSerializationContext) {
        switch (fieldBase.getFieldType()) {
            case field_string:
                return jsonSerializationContext.serialize(fieldBase, FieldString.class);
            case field_double:
                return jsonSerializationContext.serialize(fieldBase, FieldDouble.class);
            case field_long:
                return jsonSerializationContext.serialize(fieldBase, FieldLong.class);
            case field_date:
                return jsonSerializationContext.serialize(fieldBase, FieldDate.class);
        }
        throw new JsonParseException("json serialize error, unknown fieldType");
    }
}
