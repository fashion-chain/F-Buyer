package com.hottop.core.model.cms.bean;

import com.hottop.core.model.cms.bean.component.bean.DataDecorator;
import lombok.Data;

import java.io.Serializable;

@Data
public class ComponentContentDto implements Serializable {
    private PageContent pageContent;
    private DataDecorator dataDecorator;
}
