package com.hottop.core.model.community;

import com.hottop.core.model.community.bean.ETopicType;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.converter.MediaConverter;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Topic extends EntityBase {
    @Column(columnDefinition = "VARCHAR(31) NOT NULL COMMENT '标题' ")
    private String title;

    @Column(columnDefinition = "VARCHAR(31) NOT NULL COMMENT '描述' ")
    private String description;

    @Column(columnDefinition = "JSON COMMENT '图标' ")
    @Convert(converter = MediaConverter.class)
    private Image logo;

    @Column(columnDefinition = "JSON COMMENT '顶部背景' ")
    @Convert(converter = MediaConverter.class)
    private Image topBackground;

    @Column(columnDefinition = "INT(11) DEFAULT 0 COMMENT '页面ID' ")
    private Long pageId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '话题类型' ")
    private ETopicType topicType;
}
