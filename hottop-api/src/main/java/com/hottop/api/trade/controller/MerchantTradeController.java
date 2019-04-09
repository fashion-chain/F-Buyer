package com.hottop.api.trade.controller;

import com.hottop.api.base.controller.ApiBaseController;
import com.hottop.api.trade.service.MerchantTradeService;
import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/trade")
@RestController
public class MerchantTradeController extends ApiBaseController<MerchantTrade> {

    @Autowired
    private MerchantTradeService merchantTradeService;

    @Override
    public Class<MerchantTrade> clazz() {
        return MerchantTrade.class;
    }

    @Override
    public EntityBaseService service() {
        return this.merchantTradeService;
    }
}
