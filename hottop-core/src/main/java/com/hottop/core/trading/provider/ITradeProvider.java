package com.hottop.core.trading.provider;

import com.hottop.core.model.merchant.enums.ETradeProvider;
<<<<<<< HEAD

public interface ITradeProvider {
    ETradeProvider getProvider();
=======
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public interface ITradeProvider {
    ETradeProvider provider();

    String getNotifyUrl();
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

}
