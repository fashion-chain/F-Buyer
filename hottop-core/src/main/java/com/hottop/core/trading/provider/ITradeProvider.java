package com.hottop.core.trading.provider;

import com.hottop.core.model.merchant.enums.ETradeProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public interface ITradeProvider {
    ETradeProvider provider();

    String getNotifyUrl();

}
