package com.hottop.core.trading.currency;

import com.hottop.core.model.merchant.enums.ECurrency;

public abstract class CurrencyBase implements ICurrency {
    private ECurrency currency, exchangeCurrency;
    private Long amount;

    public CurrencyBase(ECurrency currency, ECurrency exchangeCurrency, Long amount) {
        this.currency = currency;
        this.exchangeCurrency = exchangeCurrency;
        this.amount = amount;
    }

    @Override
    public ECurrency currency() {
        return currency;
    }

    @Override
    public ECurrency exchangeCurrency() {
        return exchangeCurrency;
    }

    @Override
    public Long amount() {
        return amount;
    }
}
