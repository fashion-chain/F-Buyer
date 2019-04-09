package com.hottop.core.trading.handler;

import com.hottop.core.trading.currency.CnyCurrency;
import com.hottop.core.trading.provider.WxProvider;

import javax.servlet.http.HttpServletRequest;

public class WxCnyHandler extends CnyHandlerBase<WxProvider> {

    public WxCnyHandler(WxProvider provider) {
        super(provider);
    }

    @Override
    public void payment(CnyCurrency currency) {
    }
}
