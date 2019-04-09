package com.hottop.core.trading.exception;

import com.hottop.core.model.merchant.enums.ECurrency;

public class TradeCurrencyException extends Exception {
    public TradeCurrencyException(ECurrency currency) {
        super(String.format("currency:%s error.", currency.name()));
    }
}
