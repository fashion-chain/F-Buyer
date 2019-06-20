package com.hottop.core.model.community;

<<<<<<< HEAD
import com.hottop.core.model.zpoj.converter.ListLongConverter;
import com.hottop.core.model.zpoj.converter.ListStringConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.ArrayList;

@Data
@Entity
public class Community extends Container {

    @Column(columnDefinition = "JSON COMMENT '社区管理员' ")
    @Convert(converter = ListLongConverter.class)
    private ArrayList<Long> moderators;

    @Column(columnDefinition = "JSON COMMENT '社区标签' ")
    @Convert(converter = ListStringConverter.class)
    private ArrayList<String> tags;
=======
import com.hottop.core.model.cms.bean.IComponentDecorator;
import com.hottop.core.model.cms.bean.IContentDecorator;
import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.ComponentFeedList;
import com.hottop.core.model.cms.bean.component.EComponentType;
import com.hottop.core.model.community.bean.ECommunityType;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.converter.MediaConverter;
import com.hottop.core.utils.CommonUtil;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"title", "communityType"})
})
public class Community extends EntityBase implements IContentDecorator {

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
    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '社区类型' ")
    private ECommunityType communityType;

    @Override
    public EComponentType decorateComponentType() {
        return EComponentType.feedList;
    }

    @Override
    public IComponentDecorator decorator() {
        return new IComponentDecorator() {
            @Override
            public void decorate(ComponentBase component) {
                if (validate(component)) {
                    ComponentFeedList feedListComponent = (ComponentFeedList) component;
                    feedListComponent.setUrl(CommonUtil.setOrReplaceQueryParam(feedListComponent.getUrl(),
                            "communityId",
                            getId()));
                }
            }

            @Override
            public boolean validate(ComponentBase component) {
                return component != null && component instanceof ComponentFeedList;
            }
        };
    }
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
}
