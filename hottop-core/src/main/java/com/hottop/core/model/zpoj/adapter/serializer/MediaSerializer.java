package com.hottop.core.model.zpoj.adapter.serializer;

import com.google.gson.*;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.bean.Media;
import com.hottop.core.model.zpoj.bean.Video;
<<<<<<< HEAD
import com.hottop.core.model.zpoj.cms.IAction;
import com.hottop.core.model.zpoj.cms.enums.EActionType;
=======
import com.hottop.core.utils.CommonUtil;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

import java.lang.reflect.Type;

public class MediaSerializer implements JsonSerializer<Media>, JsonDeserializer<Media> {

    @Override
    public Media deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        switch (Media.EMediaType.valueOf(jsonElement.getAsJsonObject().get("type").getAsString())) {
            case image:
<<<<<<< HEAD
                return jsonDeserializationContext.deserialize(jsonElement, Image.class);
            case video:
                return jsonDeserializationContext.deserialize(jsonElement, Video.class);
        }
        throw new JsonParseException("json deserialize error, unknown actionType");
=======
                Image image = jsonDeserializationContext.deserialize(jsonElement, Image.class);
                image.setUrl(CommonUtil.imageSetUrlPrefix(image.getUrl()));
                return image;
            case video:
                Video video = jsonDeserializationContext.deserialize(jsonElement, Video.class);
                video.setUrl(CommonUtil.imageSetUrlPrefix(video.getUrl()));
                return video;
        }
        throw new JsonParseException("json deserialize error, unknown type");
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    }

    @Override
    public JsonElement serialize(Media media, Type type, JsonSerializationContext jsonSerializationContext) {
        switch (media.getMediaType()) {
            case image:
                return jsonSerializationContext.serialize(media, Image.class);
            case video:
                return jsonSerializationContext.serialize(media, Video.class);
        }
<<<<<<< HEAD
        throw new JsonParseException("json serialize error, unknown actionType");
=======
        throw new JsonParseException("json serialize error, unknown type");
    }

    public static void main(String[] args) {
        String str = "https://fashionet-test.oss-cn-beijing.aliyuncs.com/mm/ff";
        String s = str.replaceAll("(http|https)://.*?/", "/");
        System.out.println(s);
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    }

}
