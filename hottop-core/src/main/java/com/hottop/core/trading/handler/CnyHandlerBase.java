package com.hottop.core.trading.handler;

import com.hottop.core.model.merchant.enums.ETradeOperate;
import com.hottop.core.trading.currency.CnyCurrency;
import com.hottop.core.trading.exception.TradeHandlerException;
import com.hottop.core.trading.provider.ITradeProvider;

import javax.servlet.http.HttpServletRequest;

public abstract class CnyHandlerBase<T extends ITradeProvider> implements ITradingHandler<T, CnyCurrency> {
    private T provider;

    public CnyHandlerBase(T provider) {
        this.provider = provider;
    }

    @Override
    public T provider() {
        return this.provider;
    }

    @Override
    public void handleOperate(ETradeOperate operate, CnyCurrency currency) throws TradeHandlerException {
        switch (operate) {
            case payment:
                payment(currency);
                break;
            case refund:
                refund(currency);
                break;
            case withdraw:
                withdraw(currency);
                break;
            default:
                throw new TradeHandlerException(this);
        }
    }

}
