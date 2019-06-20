package com.hottop.core.model.cms.bean.component;

import com.hottop.core.model.cms.bean.component.dto.ComponentProductDto;
import com.hottop.core.model.commerce.CommerceSpu;
import com.hottop.core.model.zpoj.bean.BusinessProvider;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.commerce.bean.CommerceSpuDto;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
public class ComponentProduct extends ComponentBase {

    //市场价
    private String marketPrice;

    //售价
    private String salesPrice;

    //标题
    private String title;

    //图片
    private Image image;

    //provider 用来展示商品对应的品牌名，品牌头像
    private BusinessProvider provider;

    //collector商品收藏
    private String id;

    public ComponentProduct() {
        super(EComponentType.product);
    }
}
