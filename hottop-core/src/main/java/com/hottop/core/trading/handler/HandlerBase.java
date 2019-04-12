package com.hottop.core.trading.handler;

import com.hottop.core.trading.Trade;
import com.hottop.core.trading.currency.CnyCurrency;
import com.hottop.core.trading.currency.ICurrency;
import com.hottop.core.trading.exception.TradeHandlerException;
import com.hottop.core.trading.provider.ITradeProvider;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class HandlerBase<T extends ITradeProvider, Y extends ICurrency> implements ITradingHandler<T, Y> {

    @Override
    public void handleOperate(Trade trade, Trade.TradeItem item) throws TradeHandlerException {
        if (!isValidTradeAndItem(trade, item)) {
            throw new TradeHandlerException(this);
        }
        switch (trade.getTradeOperate()) {
            case payment:
                payment(trade, item);
                break;
            case refund:
                refund(trade, item);
                break;
            case withdraw:
                withdraw(trade, item);
                break;
            default:
                throw new TradeHandlerException(this);
        }
    }

    private boolean isValidTradeAndItem(Trade trade, Trade.TradeItem item) {
        boolean hasNoDuplicatedParameters = true, containsAllRequiredParameters = true, allAreListedParameter = true;
        HashMap<String, Object> extraParameters = new HashMap<>(trade.getExtraParams());
        for (Map.Entry<String, Object> entry: item.getExtraParams().entrySet()) {
            if (extraParameters.containsKey(entry.getKey())) {
                hasNoDuplicatedParameters = false;
                break;
            }
        }
        if (hasNoDuplicatedParameters) {
            for (String requiredParameter: provider().requiredParamKeys()) {
                if (!extraParameters.containsKey(requiredParameter)) {
                    containsAllRequiredParameters = false;
                    break;
                }
            }
            for (Map.Entry<String, Object> entry: extraParameters.entrySet()) {
                boolean isRequiredParam = false, isOptionalParam = false;
                for (String requiredParameter: provider().requiredParamKeys()) {
                    if (StringUtils.equals(entry.getKey(), requiredParameter)) {
                        isRequiredParam = true;
                    }
                }
                for (String optionalParam: provider().optionalParamKeys()) {
                    if (StringUtils.equals(entry.getKey(), optionalParam)) {
                        isOptionalParam = true;
                    }
                }
                if (!isRequiredParam && !isOptionalParam) {
                    allAreListedParameter = false;
                }
            }
        }
        return hasNoDuplicatedParameters && containsAllRequiredParameters && allAreListedParameter;
    }
}
