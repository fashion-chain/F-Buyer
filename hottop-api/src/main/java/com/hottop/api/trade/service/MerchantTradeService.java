package com.hottop.api.trade.service;

import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.merchant.MerchantTradeRepository;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.trading.Trade;
import com.hottop.core.trading.TradeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MerchantTradeService extends EntityBaseService<MerchantTrade, Long> {

    @Autowired
    private MerchantTradeRepository merchantTradeRepository;

    @Autowired
    private TradeFactory tradeFactory;

<<<<<<< HEAD
    public void handleTrade(HttpServletRequest request, Trade trade) throws Exception {
        this.tradeFactory.handle(request, trade);
    }

=======
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    @Override
    public EntityBaseRepository<MerchantTrade, Long> repository() {
        return this.merchantTradeRepository;
    }
}
