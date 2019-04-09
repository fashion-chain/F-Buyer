package com.hottop.core.model.zpoj.cms.component;

import com.hottop.core.model.zpoj.cms.enums.EComponentType;
import lombok.Data;

@Data
public class ComponentText extends ComponentBase {
    private String text;

    public ComponentText(String text) {
        super(EComponentType.text);
        this.text = text;
    }
}
