package com.hottop.core.model.cms;

import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.cms.ITemplate;
import com.hottop.core.model.zpoj.converter.ListStringConverter;
import com.hottop.core.model.zpoj.converter.MapTemplateConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashMap;

@Data
@Entity
public class Template extends EntityBase {

    @Column(columnDefinition = "VARCHAR(31) NOT NULL UNIQUE COMMENT '页面名' ")
    private String name;

    @Column(columnDefinition = "JSON COMMENT '功能模块' ")
    @Convert(converter = MapTemplateConverter.class)
    private HashMap<String, ITemplate> templates;

    @Column(columnDefinition = "JSON COMMENT '功能排序' ")
    @Convert(converter = ListStringConverter.class)
    private ArrayList<String> templateOrder;
}
