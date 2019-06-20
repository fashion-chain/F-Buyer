package com.hottop.core.model.cms.bean.component;

import com.hottop.core.model.zpoj.bean.Image;
import lombok.Data;

//系列组件component
@Data
public class ComponentSeries extends ComponentBase{

    public ComponentSeries() {
        super(EComponentType.series);
    }

    private Image image;

    private String title;

    private String description;
}
