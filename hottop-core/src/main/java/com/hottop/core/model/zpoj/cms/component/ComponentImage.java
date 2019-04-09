package com.hottop.core.model.zpoj.cms.component;

import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.cms.enums.EComponentType;
import lombok.Data;

@Data
public class ComponentImage extends ComponentBase {
    private Image image;

    public ComponentImage(Image image) {
        super(EComponentType.image);
        this.image = image;
    }

    @Override
    public EComponentType getComponentType() {
        return EComponentType.image;
    }
}
