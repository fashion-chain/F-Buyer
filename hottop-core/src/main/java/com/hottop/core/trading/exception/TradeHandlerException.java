package com.hottop.core.trading.exception;

import com.hottop.core.trading.handler.ITradingHandler;

public class TradeHandlerException extends Exception {
    public TradeHandlerException(ITradingHandler handler) {
<<<<<<< HEAD
        super(String.format("handler exception. provider: %s", handler.provider().getProvider().name()));
=======
        super(String.format("handler exception. provider: %s", handler.provider().provider().name()));
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    }
}
