package com.hottop.backstage.merchant.controller;

import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.backstage.merchant.service.MerchantTradeService;
import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.repository.merchant.MerchantTradeRepository;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trade")
public class MerchantTradeController extends BackstageBaseController<MerchantTrade> {
    @Autowired
    private MerchantTradeService merchantTradeService;

    @Autowired
    private MerchantTradeRepository merchantTradeRepository;

    @Override
    public Class<MerchantTrade> clazz() {
        return MerchantTrade.class;
    }

    @Override
    public EntityBaseService service() {
        return merchantTradeService;
    }
}
