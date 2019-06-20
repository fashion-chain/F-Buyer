package com.hottop.core.model.cms.bean.extractor;

import com.hottop.core.model.cms.annotation.ComponentConverter;
import com.hottop.core.model.cms.bean.action.ActionBase;
import com.hottop.core.model.cms.bean.action.ActionRetailProductDetail;
import com.hottop.core.model.cms.bean.action.EActionType;
import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.ComponentRetailProduct;
import com.hottop.core.model.cms.bean.component.EComponentType;
import com.hottop.core.model.commerce.CommerceBrand;
import com.hottop.core.model.commerce.CommerceRetailSpu;
import com.hottop.core.model.commerce.CommerceSpu;
import com.hottop.core.model.zpoj.bean.*;
import com.hottop.core.product.service.CommerceBrandService;
import com.hottop.core.product.service.CommerceRetailSpuService;
import com.hottop.core.product.service.CommerceSpuService;
import com.hottop.core.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

//retailProduct 零售商品 查询结果转化器
//componentAdvice 处理
@Component
public class CommerceRetailProductExtractor implements ICmsExtractor<CommerceRetailSpu>{

    @Autowired
    private CommerceSpuService commerceSpuService;

    @Autowired
    private CommerceRetailSpuService commerceRetailSpuService;

    @Autowired
    private CommerceBrandService commerceBrandService;

    @Override
    public ActionBase extractAction(CommerceRetailSpu obj, EActionType actionType) {
        switch (actionType) {
            case retailProductDetail:
                return new ActionRetailProductDetail(obj.getId());
        }
        return null;
    }

    @Override
    public ComponentBase extractComponent(CommerceRetailSpu obj, EComponentType componentType) {
        switch (componentType) {
            case retailProduct:
                return retailSpuToComponent(obj);
        }
        return null;
    }

    @Override
    public EComponentType defaultComponentType() {
        return EComponentType.retailProduct;
    }

    @Override
    public EActionType defaultActionType() {
        return EActionType.retailProductDetail;
    }

    @Override
    public Class<?> clazz() {
        return CommerceRetailSpu.class;
    }

    /**
     * 零售商品转化成组件component
     * commerceRetailSpu -> componentRetailProduct
     */
    public ComponentRetailProduct retailSpuToComponent(CommerceRetailSpu retailSpu) {
        CommerceSpu spu = commerceSpuService.getSpuDetail(retailSpu.getSpuId());
        ComponentRetailProduct result = new ComponentRetailProduct();
        result.setMarketPrice(retailSpu.getMarketPrice());
        result.setSalesPrice(commerceRetailSpuService.getSalePriceSection(retailSpu.getSpuId()));
        result.setTitle(spu.getTitle());
        ArrayList<Media> carousel = spu.getCarousel();
        for(Media media : carousel) {
            if(media instanceof Video) continue;
            if(media instanceof Image) {
                result.setImage((Image) media);
                break;
            }
        }
        CommerceBrand brand = commerceBrandService.findOne(CommerceBrand.class, spu.getBrandId());
        BusinessProvider businessProvider = new BusinessProvider();
        businessProvider.setProviderId(String.valueOf(brand.getId()));
        businessProvider.setEBusinessProviderType(EBusinessProviderType.brand);
        result.setProvider(businessProvider);
        return result;
    }
}
