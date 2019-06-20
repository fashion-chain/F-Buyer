package com.hottop.core.model.cms;

import com.hottop.core.model.cms.bean.TemplateContent;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.converter.TemplateContentConverter;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Template extends EntityBase {
    @Column(columnDefinition = "VARCHAR(255) NOT NULL UNIQUE COMMENT '模板名' ")
    private String name;

    @Column(columnDefinition = "JSON COMMENT '模版内容' ")
    @Convert(converter = TemplateContentConverter.class)
    private TemplateContent content;

}
