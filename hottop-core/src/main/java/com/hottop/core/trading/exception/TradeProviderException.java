package com.hottop.core.trading.exception;

import com.hottop.core.model.merchant.enums.ETradeProvider;

public class TradeProviderException extends Exception {
    public TradeProviderException(ETradeProvider provider) {
        super(String.format("provider:%s error.", provider.name()));
    }
}
