package com.hottop.core.feature.status;

import com.hottop.core.BaseConstant;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class Status implements Serializable {
    private String status;
    private String symbol;

    public Status(String symbol, String... parts) {
        this.symbol = symbol;
        this.status = String.format("%s%s%s", symbol, BaseConstant.Common.COMMON_SPLITTER, StringUtils.join(parts, BaseConstant.Common.COMMON_SPLITTER));
    }

    public String status() {
        return this.status;
    }

    public String symbol() {
        return this.symbol;
    }
}
