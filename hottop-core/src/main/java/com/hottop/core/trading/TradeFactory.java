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

import java.util.HashMap;

public class TradeFactory {

    private HashMap<ETradeProvider, HashMap<ECurrency, ITradingHandler>> handlerMapper;
    private HashMap<ETradeProvider, ITradeProvider> providerMapper;

    public TradeFactory() {
        this.handlerMapper = new HashMap<>();
        this.providerMapper = new HashMap<>();
    }

    public void handle(Trade trade) throws Exception {
        for (Trade.TradeItem tradeItem: trade.getTradeItems()) {
            ITradingHandler handler = getHandler(trade.getTradeProvider(), tradeItem.getCurrency());
            handler.handleOperate(trade.getTradeOperate(),
                    getCurrency(tradeItem.getCurrency(),
                            tradeItem.getAmount()));
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
        if (this.providerMapper.containsKey(tradeProvider)) {
            return this.providerMapper.get(tradeProvider);
        }
        ITradeProvider provider = null;
        switch (tradeProvider) {
            case wx:
                provider = new WxProvider();
                this.providerMapper.put(tradeProvider, provider);
                break;
            case ali:
                break;
            default:
                throw new TradeProviderException(tradeProvider);
        }
        return this.providerMapper.get(tradeProvider);
    }

    private ITradingHandler getHandler(ETradeProvider provider,
                                      ECurrency currency) throws Exception {
        if (this.handlerMapper.containsKey(provider) && this.handlerMapper.get(provider).containsKey(currency)) {
            return this.handlerMapper.get(provider).get(currency);
        }
        ITradingHandler handler = null;
        switch (provider) {
            case wx:
                if (!this.handlerMapper.containsKey(provider)) {
                    this.handlerMapper.put(provider, new HashMap<>());
                }
                switch (currency) {
                    case cny:
                        WxProvider wxProvider = (WxProvider) getProvider(provider);
                        handler = new WxCnyHandler(wxProvider);
                        this.handlerMapper.get(provider).put(currency, handler);
                        break;
                }
                break;
            case ali:
                break;
            default:
                break;
        }

        return this.handlerMapper.get(provider).get(currency);
    }

}
