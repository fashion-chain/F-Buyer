package com.hottop.core.trading.handler;

import com.hottop.core.model.merchant.enums.ETradeOperate;
import com.hottop.core.trading.Trade;
import com.hottop.core.trading.currency.ICurrency;
import com.hottop.core.trading.exception.TradeHandlerException;
import com.hottop.core.trading.provider.ITradeProvider;

public interface ITradingHandler<T extends ITradeProvider, Y extends ICurrency> {
    T provider();

    Y currency();

    void handleOperate(Trade trade, Trade.TradeItem item) throws TradeHandlerException;

    default void payment(Trade trade, Trade.TradeItem item) {}

    default void refund(Trade trade, Trade.TradeItem item) {}

    default void withdraw(Trade trade, Trade.TradeItem item) {}
}
