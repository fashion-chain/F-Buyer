package com.hottop.core.model.cms.bean.component;

import com.hottop.core.model.zpoj.bean.Image;
import lombok.Getter;

@Getter
public class ComponentImage extends ComponentBase {
    private Image image;

    public ComponentImage(Image image) {
        super(EComponentType.image);
        this.image = image;
    }
}
