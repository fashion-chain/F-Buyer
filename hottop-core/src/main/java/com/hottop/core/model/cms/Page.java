package com.hottop.core.model.cms;

<<<<<<< HEAD
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.cms.IWrapper;
import com.hottop.core.model.zpoj.converter.ListStringConverter;
import com.hottop.core.model.zpoj.converter.MapWrapperConverter;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;

@Data
@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"name", "version"})
})
public class Page extends EntityBase {
    @Column(columnDefinition = "VARCHAR(63) NOT NULL COMMENT '页面名' ")
    private String name;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '页面版本' ")
    private Integer version;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '状态' ")
    private Integer state;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '模版ID' ")
    private Long templateId;

    @Column(columnDefinition = "JSON NOT NULL COMMENT '内容' ")
    @Convert(converter = MapWrapperConverter.class)
    private HashMap<String, HashMap<String, IWrapper>> content;

    @Column(columnDefinition = "JSON COMMENT '功能排序' ")
    @Convert(converter = ListStringConverter.class)
    private ArrayList<String> pageOrder;
=======
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
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
}
