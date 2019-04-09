package com.hottop.core.model.zpoj.adapter.serializer;

import com.google.gson.*;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.zpoj.cms.template.ComponentTemplate;
import com.hottop.core.model.zpoj.cms.ITemplate;
import com.hottop.core.model.zpoj.cms.template.WidgetTemplate;
import com.hottop.core.model.zpoj.cms.enums.ETemplateType;
import org.springframework.util.Assert;

import java.lang.reflect.Type;

public class TemplateSerializer implements JsonSerializer<ITemplate>, JsonDeserializer<ITemplate> {

    @Override
    public ITemplate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Assert.notNull(jsonElement.getAsJsonObject().get("templateType"), BaseConfiguration.getMessage("request.argument.template_type", jsonElement.getAsJsonObject().get("templateType")));
        switch (ETemplateType.valueOf(jsonElement.getAsJsonObject().get("templateType").getAsString())) {
            case component:
                return jsonDeserializationContext.deserialize(jsonElement, ComponentTemplate.class);
            case widget:
                return jsonDeserializationContext.deserialize(jsonElement, WidgetTemplate.class);
        }
        throw new JsonParseException("json deserialize error, unknown templateType");
    }

    @Override
    public JsonElement serialize(ITemplate template, Type type, JsonSerializationContext jsonSerializationContext) {
        switch (template.getTemplateType()) {
            case component:
                return jsonSerializationContext.serialize(template, ComponentTemplate.class);
            case widget:
                return jsonSerializationContext.serialize(template, WidgetTemplate.class);
        }
        throw new JsonParseException("json serialize error, unknown templateType");
    }
}
