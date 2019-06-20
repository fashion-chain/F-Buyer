package com.hottop.api.order.controller;

import com.hottop.api.order.service.PurchaseOrderSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/purchaseOrderSku")
public class PurchaseOrderSkuController {

    @Autowired
    private PurchaseOrderSkuService purchaseOrderSkuService;


}
