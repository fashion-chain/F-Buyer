package com.hottop.core.model.zpoj.adapter.serializer;

import com.google.gson.*;
import com.hottop.core.model.user.AgentModule;
import com.hottop.core.model.user.DistributorModule;
import com.hottop.core.model.user.ModuleBase;
import com.hottop.core.model.user.enums.EModuleType;

import java.lang.reflect.Type;

//模块类（moduleBase），序列化器
public class ModuleSerializer implements JsonSerializer<ModuleBase>, JsonDeserializer<ModuleBase> {

    @Override
    public ModuleBase deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        switch (EModuleType.valueOf(json.getAsJsonObject().get("type").getAsString())) {
            case agent:
                return context.deserialize(json, AgentModule.class);
            case distributor:
                return context.deserialize(json, DistributorModule.class);
        }
        throw new JsonParseException("json deserialize error, unknown type");
    }

    @Override
    public JsonElement serialize(ModuleBase moduleBase, Type typeOfSrc, JsonSerializationContext context) {
        switch (moduleBase.getType()) {
            case agent:
                return context.serialize(moduleBase, AgentModule.class);
            case distributor:
                return context.serialize(moduleBase, DistributorModule.class);
        }
        throw new JsonParseException("json serialize error, unknown type");
    }
}
