package com.hottop.core.model.zpoj.adapter.serializer;

import com.google.gson.*;
import com.hottop.core.model.zpoj.cms.IWidget;
import com.hottop.core.model.zpoj.cms.enums.EWidgetType;
import com.hottop.core.model.zpoj.cms.widget.WidgetNavigator;

import java.lang.reflect.Type;

public class WidgetSerializer implements JsonSerializer<IWidget>, JsonDeserializer<IWidget> {
    @Override
    public IWidget deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        switch (EWidgetType.valueOf(jsonElement.getAsJsonObject().get("widgetType").getAsString())) {
            case navigator:
                return jsonDeserializationContext.deserialize(jsonElement, WidgetNavigator.class);
        }
        throw new JsonParseException("json deserialize error, unknown widgetType");
    }

    @Override
    public JsonElement serialize(IWidget widget, Type type, JsonSerializationContext jsonSerializationContext) {
        switch (widget.getWidgetType()) {
            case navigator:
                return jsonSerializationContext.serialize(widget, WidgetNavigator.class);
        }
        throw new JsonParseException("json serialize error, unknown fieldType");
    }
}
