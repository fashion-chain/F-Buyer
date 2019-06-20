package com.hottop.core.repository.commerce;

import com.hottop.core.model.commerce.CommercePurchaseOrder;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercePurchaseOrderRepository extends EntityBaseRepository<CommercePurchaseOrder,Long>{

    CommercePurchaseOrder findByPayTradeNo(String payTradeNo);

    //
}
