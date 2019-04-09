package com.hottop.backstage.test.controller;

import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.backstage.test.service.TestMerchantTradeService;
import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.trading.TradeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/tmp")
@RestController
public class TestMerchantTradeController extends BackstageBaseController<MerchantTrade> {

    @Autowired
    private TestMerchantTradeService testMerchantTradeService;

    @Autowired
    private TradeFactory tradeFactory;

    @RequestMapping(path = "/trade", method = RequestMethod.GET)
    public void trade(HttpServletRequest request) {

    }

    @Override
    public Class<MerchantTrade> clazz() {
        return MerchantTrade.class;
    }

    @Override
    public EntityBaseService service() {
        return this.testMerchantTradeService;
    }
}
