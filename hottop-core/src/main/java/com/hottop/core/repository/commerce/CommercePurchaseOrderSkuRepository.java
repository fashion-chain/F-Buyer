package com.hottop.core.repository.commerce;

import com.hottop.core.model.commerce.CommercePurchaseOrderSku;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommercePurchaseOrderSkuRepository extends EntityBaseRepository<CommercePurchaseOrderSku,Long> {

    //更加orderId来查询
    List<CommercePurchaseOrderSku> findAllByPurchaseOrderId(Long id);
}
