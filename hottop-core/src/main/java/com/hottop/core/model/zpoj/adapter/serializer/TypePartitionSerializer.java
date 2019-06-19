package com.hottop.core.model.zpoj.adapter.serializer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.feature.type.TypeFactory;
import com.hottop.core.feature.type.TypeIndicator;
import com.hottop.core.feature.type.TypePartition;
import com.hottop.core.utils.LogUtil;

import java.lang.reflect.Type;
import java.util.HashMap;

public class TypePartitionSerializer implements JsonDeserializer<TypePartition> {

    @Override
    public TypePartition deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        TypeIndicator typeIndicator = null;
        HashMap<String, Object> typeMeta = null;
        TypePartition typePartition = null;
        try {
            typeIndicator = TypeFactory.indicator(jsonElement.getAsJsonObject().get("type").getAsString());
            typeMeta = BaseConfiguration.simpleGson().fromJson(jsonElement.getAsJsonObject().get("typeMeta"), new TypeToken<HashMap<String, Object>>(){}.getType());
            typePartition = new TypePartition(typeIndicator, typeMeta);
            typePartition.specificTypeMeta();
        } catch (Exception ex) {
            String errorMsg = String.format("error in TypePartitionSerializer.deserialize, error message: %s", ex.getMessage());
            LogUtil.error(errorMsg);
            throw new JsonParseException(errorMsg);
        }
        return typePartition;
    }
}
