package com.hottop.core.model.cms.bean.component;

import com.hottop.core.model.zpoj.bean.Image;
import lombok.Getter;

@Getter
public class ComponentNavigator extends ComponentBase {
    private Image image;
    private String title;

    public ComponentNavigator(Image image, String title) {
        super(EComponentType.navigator);
        this.image = image;
        this.title = title;
    }
}
