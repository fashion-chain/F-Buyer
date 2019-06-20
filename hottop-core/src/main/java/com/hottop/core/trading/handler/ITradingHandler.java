package com.hottop.core.trading.handler;

import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.model.merchant.enums.ETradeOperate;
import com.hottop.core.response.Response;
import com.hottop.core.trading.Trade;
import com.hottop.core.trading.currency.ICurrency;
import com.hottop.core.trading.exception.TradeHandlerException;
import com.hottop.core.trading.provider.ITradeProvider;

import java.util.Map;

public interface ITradingHandler<T extends ITradeProvider, Y extends ICurrency> {
    T provider();

    Y currency();

    Map<String, String> initOutParams(Trade trade);

    Response handleOperate(Trade trade) throws Exception;

    default Response payment(MerchantTrade trade, Map<String, String> outParams) throws Exception {
        return null;
    }

    default Response refund(MerchantTrade trade, Map<String, String> outParams) throws Exception {
        return null;
    }

    default Response withdraw(MerchantTrade trade, Map<String, String> outParams) throws Exception {
        return null;
    }

    default Response queryTrade(String outTradeNo) throws Exception {
        return null;
    }

    default Response queryRefund(String outTradeNo) throws Exception {
        return null;
    }

}
