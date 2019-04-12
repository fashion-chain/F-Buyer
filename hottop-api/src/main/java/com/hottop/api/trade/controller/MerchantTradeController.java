package com.hottop.api.trade.controller;

import com.hottop.api.base.controller.ApiBaseController;
import com.hottop.api.trade.service.MerchantTradeService;
import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.model.merchant.enums.ECurrency;
import com.hottop.core.model.merchant.enums.ETradeOperate;
import com.hottop.core.model.merchant.enums.ETradeProvider;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.trading.Trade;
import com.hottop.core.trading.TradeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.NetworkInterface;

@RequestMapping("/trade")
@RestController
public class MerchantTradeController extends ApiBaseController<MerchantTrade> {

    @Autowired
    private MerchantTradeService merchantTradeService;

    @Autowired
    private TradeFactory tradeFactory;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Response test() throws Exception {
        Trade testTrade = Trade.TradeBuilder.init(ETradeProvider.wx, ETradeOperate.payment)
                .items(Trade.TradeBuilder.newItem(ECurrency.cny, 10L))
                .create();
        tradeFactory.handle(testTrade);
        return Response.ResponseBuilder.result(EResponseResult.OK).create();
    }

    @Override
    public Class<MerchantTrade> clazz() {
        return MerchantTrade.class;
    }

    @Override
    public EntityBaseService service() {
        return this.merchantTradeService;
    }
}
