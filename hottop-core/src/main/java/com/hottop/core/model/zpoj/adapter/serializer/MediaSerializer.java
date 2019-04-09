package com.hottop.core.model.zpoj.adapter.serializer;

import com.google.gson.*;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.bean.Media;
import com.hottop.core.model.zpoj.bean.Video;
import com.hottop.core.model.zpoj.cms.IAction;
import com.hottop.core.model.zpoj.cms.enums.EActionType;

import java.lang.reflect.Type;

public class MediaSerializer implements JsonSerializer<Media>, JsonDeserializer<Media> {

    @Override
    public Media deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        switch (Media.EMediaType.valueOf(jsonElement.getAsJsonObject().get("type").getAsString())) {
            case image:
                return jsonDeserializationContext.deserialize(jsonElement, Image.class);
            case video:
                return jsonDeserializationContext.deserialize(jsonElement, Video.class);
        }
        throw new JsonParseException("json deserialize error, unknown actionType");
    }

    @Override
    public JsonElement serialize(Media media, Type type, JsonSerializationContext jsonSerializationContext) {
        switch (media.getMediaType()) {
            case image:
                return jsonSerializationContext.serialize(media, Image.class);
            case video:
                return jsonSerializationContext.serialize(media, Video.class);
        }
        throw new JsonParseException("json serialize error, unknown actionType");
    }

}
