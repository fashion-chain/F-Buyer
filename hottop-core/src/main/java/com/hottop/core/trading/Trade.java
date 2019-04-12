package com.hottop.core.trading;

import com.hottop.core.model.merchant.enums.ECurrency;
import com.hottop.core.model.merchant.enums.ETradeOperate;
import com.hottop.core.model.merchant.enums.ETradeProvider;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class Trade {
    private ETradeProvider tradeProvider;
    private ETradeOperate tradeOperate;
    private ArrayList<TradeItem> tradeItems;
    private HashMap<String, Object> extraParams;

    public ETradeProvider getTradeProvider() {
        return tradeProvider;
    }

    public ETradeOperate getTradeOperate() {
        return tradeOperate;
    }

    public ArrayList<TradeItem> getTradeItems() {
        return tradeItems;
    }

    public HashMap<String, Object> getExtraParams() {
        return extraParams;
    }

    @Data
    public static class TradeItem {
        private ECurrency currency;
        private Long amount;
        private HashMap<String, Object> extraParams;

        private TradeItem(ECurrency currency, Long amount) {
            this.currency = currency;
            this.amount = amount;
            this.extraParams = new HashMap<>();
        }

        public static TradeItem newItem(ECurrency currency, Long amount) {
            return new TradeItem(currency, amount);
        }

        public TradeItem param(String key, Object value) {
            this.extraParams.put(key, value);
            return this;
        }

        public TradeItem params(HashMap<String, Object> extraParams) {
            this.extraParams.putAll(extraParams);
            return this;
        }
    }

    public static class TradeBuilder {
        private ETradeProvider provider;
        private ETradeOperate tradeOperate;
        private ArrayList<TradeItem> tradeItems;
        private HashMap<String, Object> extraParams;

        private TradeBuilder(ETradeProvider provider, ETradeOperate tradeOperate) {
            this.provider = provider;
            this.tradeOperate = tradeOperate;
            this.tradeItems = new ArrayList<>();
            this.extraParams = new HashMap<>();
        }

        public static TradeBuilder init(ETradeProvider provider, ETradeOperate tradeOperate) {
            return new TradeBuilder(provider, tradeOperate);
        }

        public TradeBuilder items(List<TradeItem> items) {
            this.tradeItems.addAll(items);
            return this;
        }

        public TradeBuilder items(TradeItem... items) {
            return items(Arrays.asList(items));
        }

        public TradeBuilder param(String key, Object value) {
            this.extraParams.put(key, value);
            return this;
        }

        public TradeBuilder params(HashMap<String, Object> extraParams) {
            this.extraParams.putAll(extraParams);
            return this;
        }

        public Trade create() {
            return new Trade(this.provider, this.tradeOperate, this.tradeItems, this.extraParams);
        }
    }
}
