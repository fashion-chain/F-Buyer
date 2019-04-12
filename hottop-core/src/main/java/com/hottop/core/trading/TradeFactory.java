package com.hottop.core.trading;

import com.hottop.core.model.merchant.enums.ECurrency;
import com.hottop.core.model.merchant.enums.ETradeProvider;
import com.hottop.core.trading.currency.CnyCurrency;
import com.hottop.core.trading.currency.ICurrency;
import com.hottop.core.trading.exception.TradeCurrencyException;
import com.hottop.core.trading.exception.TradeProviderException;
import com.hottop.core.trading.handler.ITradingHandler;
import com.hottop.core.trading.handler.WxCnyHandler;
import com.hottop.core.trading.provider.ITradeProvider;
import com.hottop.core.trading.provider.WxProvider;

public class TradeFactory {

    public TradeFactory() {
    }

    public void handle(Trade trade) throws Exception {
        for (Trade.TradeItem tradeItem: trade.getTradeItems()) {
            ITradeProvider provider = getProvider(trade.getTradeProvider());
            ICurrency currency = getCurrency(tradeItem.getCurrency(), tradeItem.getAmount());
            ITradingHandler handler = getHandler(provider, currency);
            handler.handleOperate(trade, tradeItem);
        }
    }

    private ICurrency getCurrency(ECurrency currencyType, Long amount) throws TradeCurrencyException {
        ICurrency currency = null;
        switch (currencyType) {
            case cny:
                currency = new CnyCurrency(amount);
                break;
            default:
                throw new TradeCurrencyException(currencyType);
        }
        return currency;
    }

    private ITradeProvider getProvider(ETradeProvider tradeProvider) throws TradeProviderException {
        ITradeProvider provider = null;
        switch (tradeProvider) {
            case wx:
                provider = new WxProvider();
            case ali:
                break;
            default:
                throw new TradeProviderException(tradeProvider);
        }
        return provider;
    }

    private ITradingHandler getHandler(ITradeProvider provider,
                                      ICurrency currency) {
        ITradingHandler handler = null;
        switch (provider.provider()) {
            case wx:
                switch (currency.currency()) {
                    case cny:
                        handler = new WxCnyHandler((WxProvider) provider, (CnyCurrency) currency);
                        break;
                }
                break;
            case ali:
                break;
            default:
                break;
        }

        return handler;
    }

}
