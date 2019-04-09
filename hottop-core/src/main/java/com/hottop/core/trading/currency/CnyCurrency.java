package com.hottop.core.trading.currency;

import com.hottop.core.model.merchant.enums.ECurrency;

public class CnyCurrency extends CurrencyBase {

    public CnyCurrency(Long amount) {
        super(ECurrency.cny, ECurrency.cny, amount);
    }

    @Override
    public Double exchangeRate() {
        return 1.0d;
    }

    @Override
    public String symbol() {
        return "¥";
    }

    @Override
    public String name() {
        return "元";
    }
}
