package com.hottop.core.trading.handler;

import com.hottop.core.trading.Trade;
import com.hottop.core.trading.currency.CnyCurrency;
import com.hottop.core.trading.provider.WxProvider;

import java.util.HashMap;
import java.util.Map;

public class WxCnyHandler extends HandlerBase<WxProvider, CnyCurrency> {

    private CnyCurrency currency;
    private WxProvider provider;

    public WxCnyHandler(WxProvider provider, CnyCurrency currency) {
        this.provider = provider;
        this.currency = currency;
    }

    @Override
    public CnyCurrency currency() {
        return currency;
    }

    @Override
    public WxProvider provider() {
        return provider;
    }

    @Override
    public void payment(Trade trade, Trade.TradeItem item) {

    }
}
