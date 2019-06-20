package com.hottop.core.model.cms.bean.component;

import com.hottop.core.model.zpoj.bean.ETextAlign;
import lombok.Getter;

@Getter
public class ComponentText extends ComponentBase {
    private String text;
    private Double fontSize;
    private ETextAlign align;

    public ComponentText(String text) {
        super(EComponentType.text);
        this.text = text;
    }

    public ComponentText(String text, Double fontSize, ETextAlign align) {
        this(text);
        this.fontSize = fontSize;
        this.align = align;
    }

}
