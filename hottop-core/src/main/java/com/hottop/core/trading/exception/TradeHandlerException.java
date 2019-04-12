package com.hottop.core.trading.exception;

import com.hottop.core.trading.handler.ITradingHandler;

public class TradeHandlerException extends Exception {
    public TradeHandlerException(ITradingHandler handler) {
        super(String.format("handler exception. provider: %s", handler.provider().provider().name()));
    }
}
