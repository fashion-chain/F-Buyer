package com.hottop.core.model.cms;

import com.hottop.core.model.cms.bean.PageContent;
import com.hottop.core.model.cms.bean.TemplateContent;
import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class Activity extends EntityBase {
    @Column(columnDefinition = "VARCHAR(255) NOT NULL COMMENT '标题' ")
    private String title;

    @Column(columnDefinition = "VARCHAR(2047) NOT NULL COMMENT '描述' ")
    private String description;

    @Column(columnDefinition = "JSON COMMENT '页面内容' ")
    private PageContent pageContent;
}
