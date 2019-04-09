package com.hottop.core.model.zpoj.bean;

import lombok.Data;

import java.io.Serializable;

public abstract class Media implements Serializable {
    private EMediaType type;

    private String url;
    private String uuid;
    private String format;

    public Media(EMediaType type, String url, String uuid, String format) {
        this.type = type;
        this.url = url;
        this.uuid = uuid;
        this.format = format;
    }

    public String getUuid() {
        return uuid;
    }

    public EMediaType getMediaType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getFormat() {
        return format;
    }

    public enum EMediaType {
        image, video
    }

    @Data
    static class MediaBuilder {
        private EMediaType type;
        private String uuid;
        private String url;
        private String format;

        MediaBuilder initMedia(EMediaType type, String uuid, String url, String format) {
            this.setType(type);
            this.setUuid(uuid);
            this.setUrl(url);
            this.setFormat(format);
            return this;
        }
    }
}
