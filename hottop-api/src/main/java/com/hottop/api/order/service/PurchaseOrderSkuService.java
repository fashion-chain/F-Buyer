package com.hottop.api.order.service;

import com.hottop.core.model.commerce.CommercePurchaseOrderSku;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.commerce.CommercePurchaseOrderSkuRepository;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderSkuService extends EntityBaseService<CommercePurchaseOrderSku,Long>{

    @Autowired
    private CommercePurchaseOrderSkuRepository commercePurchaseOrderSkuRepository;

    @Override
    public EntityBaseRepository<CommercePurchaseOrderSku, Long> repository() {
        return commercePurchaseOrderSkuRepository;
    }

}
