package com.hottop.core.model.community;

import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.bean.Rule;
import com.hottop.core.model.zpoj.converter.ImageConverter;
import com.hottop.core.model.zpoj.converter.ListRuleConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;

@Data
@MappedSuperclass
public abstract class Container extends EntityBase {

    @Column(columnDefinition = "VARCHAR(31) NOT NULL COMMENT '标题' ")
    private String title;

    @Column(columnDefinition = "VARCHAR(31) NOT NULL COMMENT '副标题' ")
    private String subtitle;

    @Column(columnDefinition = "VARCHAR(127) NOT NULL UNIQUE COMMENT 'slug' ")
    private String slug;

    @Column(columnDefinition = "JSON COMMENT '头像' ")
    @Convert(converter = ImageConverter.class)
    private Image avatar;

    @Column(columnDefinition = "VARCHAR(15) NOT NULL DEFAULT '' ")
    private String subscriberCount;

    @Column(columnDefinition = "JSON COMMENT '规则' ")
    @Convert(converter = ListRuleConverter.class)
    private ArrayList<Rule> rules;
}
