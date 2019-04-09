package com.hottop.core.model.merchant;

import com.hottop.core.model.merchant.enums.ETradeProvider;
import com.hottop.core.model.merchant.enums.ECurrency;
import com.hottop.core.model.merchant.enums.ETradeStatus;
import com.hottop.core.model.merchant.enums.ETradeOperate;
import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class MerchantTrade extends EntityBase {
    private ETradeOperate tradeOperate;

    private ETradeProvider provider;

    private ECurrency currency;

    private ETradeStatus status;

    private String description;

    private String tradeToken; //业务系统唯一标识

    private Long amount;

    private String outTradeNo;

    private String tradeNo;

    private String refundNo;

    private String remark;


}
