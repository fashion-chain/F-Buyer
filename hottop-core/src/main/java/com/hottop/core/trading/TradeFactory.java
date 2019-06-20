package com.hottop.core.trading;

<<<<<<< HEAD
import com.hottop.core.model.merchant.enums.ECurrency;
import com.hottop.core.model.merchant.enums.ETradeProvider;
=======
import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.model.merchant.enums.ECurrency;
import com.hottop.core.model.merchant.enums.ETradeProvider;
import com.hottop.core.repository.merchant.MerchantTradeRepository;
import com.hottop.core.response.Response;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import com.hottop.core.trading.currency.CnyCurrency;
import com.hottop.core.trading.currency.ICurrency;
import com.hottop.core.trading.exception.TradeCurrencyException;
import com.hottop.core.trading.exception.TradeProviderException;
<<<<<<< HEAD
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
=======
import com.hottop.core.trading.handler.AliCnyHandler;
import com.hottop.core.trading.handler.ITradingHandler;
import com.hottop.core.trading.handler.WxCnyHandler;
import com.hottop.core.trading.provider.AliProvider;
import com.hottop.core.trading.provider.ITradeProvider;
import com.hottop.core.trading.provider.WxProvider;
import com.hottop.core.trading.resolver.AliNotifyResolver;
import com.hottop.core.trading.resolver.INotifyResolver;
import com.hottop.core.trading.resolver.WxNotifyResolver;
import org.springframework.core.io.ResourceLoader;

import java.util.Map;

public class TradeFactory {

    private ResourceLoader resourceLoader;
    private MerchantTradeRepository merchantTradeRepository;

    public TradeFactory(ResourceLoader resourceLoader, MerchantTradeRepository merchantTradeRepository) {
        this.resourceLoader = resourceLoader;
        this.merchantTradeRepository = merchantTradeRepository;
    }

    public Object resolve(ETradeProvider provider, String outTradeNo, String tradeNo, Map<String, String> params) throws Exception {
        INotifyResolver resolver = getResolver(getProvider(provider));
        MerchantTrade trade = merchantTradeRepository.findByTradeProviderAndOutTradeNo(provider, outTradeNo);
        trade.setTradeNo(tradeNo);
        return resolver.resolve(trade, params);
    }

    public Response handle(Trade trade) throws Exception {
        ITradeProvider provider = getProvider(trade.getTradeProvider());
        ICurrency currency = getCurrency(trade.getCurrency(), trade.getAmount());
        ITradingHandler handler = getHandler(provider, currency);
        return handler.handleOperate(trade);
    }

    private INotifyResolver getResolver(ITradeProvider provider) {
        INotifyResolver resolver = null;
        switch (provider.provider()) {
            case wx:
                resolver = new WxNotifyResolver((WxProvider) provider);
                break;
            case ali:
                resolver = new AliNotifyResolver();
                break;
        }
        return resolver;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
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

<<<<<<< HEAD
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
=======
    public ITradeProvider getProvider(ETradeProvider tradeProvider) throws TradeProviderException {
        ITradeProvider provider = null;
        switch (tradeProvider) {
            case wx:
                provider = new WxProvider(resourceLoader);
                break;
            case ali:
                provider = new AliProvider();
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
                break;
            default:
                throw new TradeProviderException(tradeProvider);
        }
<<<<<<< HEAD
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
=======
        return provider;
    }

    private ITradingHandler getHandler(ITradeProvider provider,
                                      ICurrency currency) {
        ITradingHandler handler = null;
        switch (provider.provider()) {
            case wx:
                switch (currency.currency()) {
                    case cny:
                        handler = new WxCnyHandler(merchantTradeRepository, (WxProvider) provider, (CnyCurrency) currency);
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
                        break;
                }
                break;
            case ali:
<<<<<<< HEAD
=======
                switch (currency.currency()) {
                    case cny:
                        handler = new AliCnyHandler(merchantTradeRepository, (AliProvider) provider, (CnyCurrency) currency);
                        break;
                }
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
                break;
            default:
                break;
        }

<<<<<<< HEAD
        return this.handlerMapper.get(provider).get(currency);
=======
        return handler;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    }

}
