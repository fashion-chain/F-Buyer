package com.hottop.core.model.cms.bean.extractor;

import com.hottop.core.model.cms.bean.action.ActionBase;
import com.hottop.core.model.cms.bean.action.ActionProductDetail;
import com.hottop.core.model.cms.bean.action.EActionType;
import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.ComponentProduct;
import com.hottop.core.model.cms.bean.component.EComponentType;
import com.hottop.core.model.cms.bean.component.dto.ComponentProductDto;
import com.hottop.core.model.commerce.CommerceBrand;
import com.hottop.core.model.commerce.CommerceSpu;
import com.hottop.core.model.zpoj.bean.*;
import com.hottop.core.product.service.CommerceBrandService;
import com.hottop.core.product.service.CommerceSpuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

//spu 商品 提取转化器
@Component
public class CommerceProductExtractor implements ICmsExtractor<CommerceSpu> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommerceBrandService commerceBrandService;

    @Autowired
    private CommerceSpuService commerceSpuService;

    @Override
    public ActionBase extractAction(CommerceSpu obj, EActionType actionType) {
        switch (actionType){
            case productDetail:
                return new ActionProductDetail(obj.getId());
        }
        return null;
    }

    @Override
    public ComponentBase extractComponent(CommerceSpu obj, EComponentType componentType) {
        switch (componentType) {
            case product:
                return spu2product(obj);
        }
        return null;
    }

    private ComponentProduct spu2product(CommerceSpu spu) {
        ComponentProduct product = new ComponentProduct();
        CommerceBrand brand = commerceBrandService.findOne(CommerceBrand.class, spu.getBrandId());
        if(spu == null || brand == null) return product;
        product.setMarketPrice(spu.getMarketPrice());
        product.setSalesPrice(commerceSpuService.getSpuSalePriceSection(spu.getId()));
        product.setTitle(spu.getTitle());
        ArrayList<Media> carousel = spu.getCarousel();
        for(Media media : carousel) {
            if(media instanceof Video) continue;
            if(media instanceof Image) {
                product.setImage((Image) media);
                break;
            }
        }
        BusinessProvider businessProvider = new BusinessProvider();
        businessProvider.setProviderId(String.valueOf(brand.getId()));
        businessProvider.setEBusinessProviderType(EBusinessProviderType.brand);
        product.setProvider(businessProvider);
        product.setId(String.valueOf(spu.getId()));
        return product;
    }

    @Override
    public EComponentType defaultComponentType() {
        return EComponentType.product;
    }

    @Override
    public EActionType defaultActionType() {
        return EActionType.productDetail;
    }

    @Override
    public Class<?> clazz() {
        return CommerceSpu.class;
    }
}
