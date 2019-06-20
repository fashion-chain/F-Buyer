package com.hottop.core.trading;

import com.hottop.core.model.merchant.enums.ECurrency;
import com.hottop.core.model.merchant.enums.ETradeOperate;
import com.hottop.core.model.merchant.enums.ETradeProvider;
<<<<<<< HEAD
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
=======
import com.hottop.core.model.merchant.enums.ETradeSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Getter
@AllArgsConstructor
public class Trade {
    private HttpServletRequest request;

    private ETradeProvider tradeProvider;
    private ETradeOperate tradeOperate;
    private ETradeSource tradeSource;
    private ECurrency currency;
    private Long amount;

    private String tradeToken;
    private String outTradeNo;
    private String subject;
    private String description;
    private String remark;

    public static class TradeBuilder {
        private HttpServletRequest request;

        private ETradeProvider provider;
        private ETradeOperate tradeOperate;
        private ETradeSource tradeSource;
        private ECurrency currency;
        private Long amount;

        private String tradeToken;
        private String outTradeNo;
        private String subject;
        private String description;
        private String remark;

        private TradeBuilder(HttpServletRequest request, ETradeProvider provider, ETradeOperate tradeOperate, ETradeSource tradeSource) {
            this.request = request;
            this.provider = provider;
            this.tradeOperate = tradeOperate;
            this.tradeSource = tradeSource;
        }

        public static TradeBuilder init(HttpServletRequest request, ETradeProvider provider, ETradeOperate tradeOperate, ETradeSource tradeSource) {
            return new TradeBuilder(request, provider, tradeOperate, tradeSource);
        }

        public TradeBuilder currencyAmount(ECurrency currency, Long amount) {
            this.currency = currency;
            this.amount = amount;
            return this;
        }

        public TradeBuilder tradeTokenAndOutTradeNo(String tradeToken, String outTradeNo) {
            this.tradeToken = tradeToken;
            this.outTradeNo = outTradeNo;
            return this;
        }

        public TradeBuilder info(String subject, String description, String remark) {
            this.subject = subject;
            this.description = description;
            this.remark = remark;
            return this;
        }

        public Trade create() {
            return new Trade(
                    this.request,
                    this.provider,
                    this.tradeOperate,
                    this.tradeSource,
                    this.currency,
                    this.amount,
                    this.tradeToken,
                    this.outTradeNo,
                    this.subject,
                    this.description,
                    this.remark);
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
        }
    }
}
