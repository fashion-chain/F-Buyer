package com.hottop.core.model.cms.bean.component;

import com.hottop.core.model.zpoj.bean.BusinessProvider;
import com.hottop.core.model.zpoj.bean.Image;
import lombok.Data;

//零售商品component
@Data
public class ComponentRetailProduct extends ComponentBase{

    private String marketPrice;

    private String salesPrice;

    private String title;

    private Image image;

    private BusinessProvider provider;

    public ComponentRetailProduct() {
        super(EComponentType.retailProduct);
    }
}
