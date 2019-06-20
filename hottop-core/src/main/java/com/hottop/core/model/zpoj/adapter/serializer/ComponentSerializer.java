package com.hottop.core.model.zpoj.adapter.serializer;

import com.google.gson.*;
<<<<<<< HEAD
import com.hottop.core.model.zpoj.cms.IComponent;
import com.hottop.core.model.zpoj.cms.component.ComponentImage;
import com.hottop.core.model.zpoj.cms.component.ComponentText;
import com.hottop.core.model.zpoj.cms.enums.EComponentType;

import java.lang.reflect.Type;

public class ComponentSerializer implements JsonSerializer<IComponent>, JsonDeserializer<IComponent> {

    @Override
    public IComponent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        switch (EComponentType.valueOf(jsonElement.getAsJsonObject().get("componentType").getAsString())) {
            case text:
                return jsonDeserializationContext.deserialize(jsonElement, ComponentText.class);
            case image:
                return jsonDeserializationContext.deserialize(jsonElement, ComponentImage.class);
        }
        throw new JsonParseException("json deserialize error, unknown componentType");
    }

    @Override
    public JsonElement serialize(IComponent component, Type type, JsonSerializationContext jsonSerializationContext) {
        switch (component.getComponentType()) {
            case text:
                return jsonSerializationContext.serialize(component, ComponentText.class);
            case image:
                return jsonSerializationContext.serialize(component, ComponentImage.class);
        }
        throw new JsonParseException("json serialize error, unknown componentType");
    }

=======
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
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
}
