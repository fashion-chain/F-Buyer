package com.hottop.core.model.community;

import com.hottop.core.model.community.bean.FeedEntity;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.converter.FeedEntityConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;

@Entity
@Data
public class Feed extends EntityBase {

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '发布者ID' ")
    private Long userId;

    @Column(columnDefinition = "INT(11) COMMENT '社区ID' ")
    private Long communityId;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '信息内容' ")
    private String content;

    @Column(columnDefinition = "JSON COMMENT 'feed实体信息' ")
    @Convert(converter = FeedEntityConverter.class)
    private FeedEntity entities;
}
