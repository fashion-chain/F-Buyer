package com.hottop.core.trading;

import com.hottop.core.model.merchant.enums.ECurrency;
import com.hottop.core.model.merchant.enums.ETradeOperate;
import com.hottop.core.model.merchant.enums.ETradeProvider;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class Trade {
    private ETradeProvider tradeProvider;
    private ETradeOperate tradeOperate;
    private ArrayList<TradeItem> tradeItems;

    public ETradeProvider getTradeProvider() {
        return tradeProvider;
    }

    public ETradeOperate getTradeOperate() {
        return tradeOperate;
    }

    public ArrayList<TradeItem> getTradeItems() {
        return tradeItems;
    }

    public static class TradeItem {
        private ECurrency currency;
        private Long amount;

        private TradeItem(ECurrency currency, Long amount) {
            this.currency = currency;
            this.amount = amount;
        }

        public ECurrency getCurrency() {
            return currency;
        }

        public Long getAmount() {
            return amount;
        }
    }

    public static class TradeBuilder {
        private ETradeProvider provider;
        private ETradeOperate tradeOperate;
        private ArrayList<TradeItem> tradeItems;

        private TradeBuilder(ETradeProvider provider, ETradeOperate tradeOperate) {
            this.provider = provider;
            this.tradeOperate = tradeOperate;
            this.tradeItems = new ArrayList<>();
        }

        public static TradeBuilder init(ETradeProvider provider, ETradeOperate tradeOperate) {
            return new TradeBuilder(provider, tradeOperate);
        }

        public static TradeItem newItem(ECurrency currency, Long amount) {
            return new TradeItem(currency, amount);
        }

        public TradeBuilder items(List<TradeItem> items) {
            this.tradeItems.addAll(items);
            return this;
        }

        public TradeBuilder items(TradeItem... items) {
            return items(Arrays.asList(items));
        }

        public Trade create() {
            return new Trade(this.provider, this.tradeOperate, this.tradeItems);
        }
    }
}
