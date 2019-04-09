package com.hottop.core.model.zpoj.bean;

import lombok.Data;

public class Video extends Media {
    private Integer width;
    private Integer height;

    private Integer duration;
    private Integer bitRate;
    private Integer frameRate;
    private String title;

    public Video(VideoBuilder builder) {
        super(builder.getType(), builder.getUrl(), builder.getUuid(), builder.getFormat());
        this.width = builder.getWidth();
        this.height = builder.getHeight();
        this.duration = builder.getDuration();
        this.bitRate = builder.getBitRate();
        this.frameRate = builder.getFrameRate();
        this.title = builder.getTitle();
    }

    @Data
    public static class VideoBuilder extends MediaBuilder {
        private Integer width;
        private Integer height;

        private Integer duration;
        private Integer bitRate;
        private Integer frameRate;
        private String title;

        public static VideoBuilder initVideo(EMediaType type, String url, String uuid, String format, Integer width, Integer height,
                            Integer duration, Integer bitRate, Integer frameRate, String title) {
            VideoBuilder builder = new VideoBuilder();
            builder.initMedia(type, url, uuid, format);
            builder.setWidth(width);
            builder.setHeight(height);
            builder.setDuration(duration);
            builder.setBitRate(bitRate);
            builder.setFrameRate(frameRate);
            builder.setTitle(title);
            return builder;
        }

        public Video create() {
            return new Video(this);
        }
    }
}
