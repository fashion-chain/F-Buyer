package com.hottop.core.model.cms;

import com.hottop.core.model.cms.bean.component.bean.DataDecorator;
import com.hottop.core.model.cms.bean.PageContent;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.converter.DataDecoratorConverter;
import com.hottop.core.model.zpoj.converter.PageContentConverter;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Page extends EntityBase {
    @Column(columnDefinition = "VARCHAR(255) NOT NULL UNIQUE COMMENT '页面名' ")
    private String name;

    @Column(columnDefinition = "JSON COMMENT '页面内容' ")
    @Convert(converter = PageContentConverter.class)
    private PageContent pageContent;

    @Column(columnDefinition = "JSON COMMENT '数据装饰' ")
    @Convert(converter = DataDecoratorConverter.class)
    private DataDecorator dataDecorator;
}
