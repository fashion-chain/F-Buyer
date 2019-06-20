package com.hottop.core.model.cms.bean.extractor;

import com.hottop.core.model.cms.bean.action.ActionBase;
import com.hottop.core.model.cms.bean.action.ActionPurchaseOrderDetail;
import com.hottop.core.model.cms.bean.action.EActionType;
import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.ComponentPurchaseOrder;
import com.hottop.core.model.cms.bean.component.EComponentType;
import com.hottop.core.model.commerce.CommercePurchaseOrder;
import com.hottop.core.model.commerce.CommercePurchaseOrderSku;
import com.hottop.core.model.commerce.CommerceSku;
import com.hottop.core.model.zpoj.bean.BusinessProvider;
import com.hottop.core.model.zpoj.bean.BusinessProviderInfo;
import com.hottop.core.model.zpoj.bean.EBusinessProviderType;
import com.hottop.core.repository.commerce.CommercePurchaseOrderSkuRepository;
import com.hottop.core.repository.commerce.CommerceSkuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//采购单 component,action
@Component
public class CommercePurchaseOrderExtractor implements ICmsExtractor<CommercePurchaseOrder> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommercePurchaseOrderSkuRepository commercePurchaseOrderSkuRepository;

    @Override
    public ActionBase extractAction(CommercePurchaseOrder obj, EActionType actionType) {
        switch (actionType) {
            case purchaseOrderDetail:
                return new ActionPurchaseOrderDetail(obj.getId());
        }
        return null;
    }

    @Override
    public ComponentBase extractComponent(CommercePurchaseOrder obj, EComponentType componentType) {
        switch (componentType) {
            case purchaseOrder:
                return purchaseOrderToComponentPurchaseOrder(obj);
        }
        return null;
    }

    @Override
    public EComponentType defaultComponentType() {
        return EComponentType.purchaseOrder;
    }

    @Override
    public EActionType defaultActionType() {
        return EActionType.purchaseOrderDetail;
    }

    @Override
    public Class<?> clazz() {
        return CommercePurchaseOrder.class;
    }


    //purchaseOrder -> ComponentPurchaseOrder
    private ComponentPurchaseOrder purchaseOrderToComponentPurchaseOrder(CommercePurchaseOrder order) {
        ComponentPurchaseOrder componentOrder = new ComponentPurchaseOrder();
        //providerInfo
        BusinessProviderInfo providerInfo = new BusinessProviderInfo();
        String tradeNo = order.getTradeNo();
        String status = order.getStatusShowName();
        //根据采购单的id查询skus
        List<CommercePurchaseOrderSku> skus = commercePurchaseOrderSkuRepository.findAllByPurchaseOrderId(order.getId());
        String totalSkuCount = String.valueOf(skus.size());
        //@todo
        // String totalCount = order.
        String totalAmount = String.valueOf(order.getAmount());
        componentOrder.setProviderInfo(providerInfo);
        componentOrder.setTradeNo(tradeNo);
        componentOrder.setStatus(status);
        componentOrder.setSkus(skus);
        componentOrder.setTotalSkuCount(totalSkuCount);
        componentOrder.setTotalAmount(totalAmount);
        return componentOrder;
    }

}
