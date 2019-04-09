package com.hottop.core.trading.currency;

import com.hottop.core.model.merchant.enums.ECurrency;

public interface ICurrency {
    Long amount();

    ECurrency currency();

    Double exchangeRate();

    ECurrency exchangeCurrency();

    String symbol();

    String name();
}
