package com.hottop.core.trading.handler;

import com.hottop.core.model.merchant.enums.ETradeOperate;
import com.hottop.core.trading.currency.ICurrency;
import com.hottop.core.trading.exception.TradeHandlerException;
import com.hottop.core.trading.provider.ITradeProvider;

import javax.servlet.http.HttpServletRequest;

public interface ITradingHandler<T extends ITradeProvider, Y extends ICurrency> {
    T provider();

    void handleOperate(ETradeOperate operate, Y currency) throws TradeHandlerException;

    default void payment(Y currency) {}

    default void refund(Y currency) {}

    default void withdraw(Y currency) {}
}
