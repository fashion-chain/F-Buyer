package com.hottop.core.model.zpoj.bean;

import lombok.Data;

public class Image extends Media {
    private Integer width;
    private Integer height;

    private Image(ImageBuilder builder) {
        super(builder.getType(), builder.getUrl(), builder.getUuid(), builder.getFormat());
        this.width = builder.getWidth();
        this.height = builder.getHeight();
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    @Data
    public static class ImageBuilder extends MediaBuilder {
        private Integer width;
        private Integer height;

        public static ImageBuilder initImage(EMediaType type, String uuid, String url, String format, Integer width, Integer height) {
            ImageBuilder builder = new ImageBuilder();
            builder.initMedia(type, uuid, url, format);
            builder.setWidth(width);
            builder.setHeight(height);
            return builder;
        }

        public Image create() {
            return new Image(this);
        }
    }
}
