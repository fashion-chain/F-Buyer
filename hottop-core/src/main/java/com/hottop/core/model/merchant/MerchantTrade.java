package com.hottop.core.model.merchant;

import com.hottop.core.model.merchant.enums.*;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.converter.MapStringConverter;
import com.hottop.core.model.zpoj.converter.StatusTrackerConverter;
import com.hottop.core.feature.status.StatusStatusTracker;
import lombok.Data;

import javax.persistence.*;
import java.util.Map;

@Data
@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"tradeProvider", "tradeNo"})
})
public class MerchantTrade extends EntityBase {

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '操作类型' ")
    private ETradeOperate tradeOperate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '提供商' ")
    private ETradeProvider tradeProvider;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '交易来源' ")
    private ETradeSource tradeSource;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '货币类型' ")
    private ECurrency currency;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '金额' ")
    private Long amount;

    @Column(columnDefinition = "VARCHAR(31) NOT NULL COMMENT '状态' ")
    @Convert(converter = StatusTrackerConverter.class)
    private StatusStatusTracker status;

    @Column(columnDefinition = "VARCHAR(63) NOT NULL COMMENT '业务系统交易标识' ")
    private String tradeToken;

    @Column(columnDefinition = "VARCHAR(63) NOT NULL UNIQUE COMMENT '业务系统唯一交易识别号' ")
    private String outTradeNo; //业务系统唯一标识, outTradeNo

    @Column(columnDefinition = "VARCHAR(63) COMMENT '提供商唯一交易识别号' ")
    private String tradeNo;

    @Column(columnDefinition = "VARCHAR(127) NOT NULL COMMENT '标题' ")
    private String subject;

    @Column(columnDefinition = "VARCHAR(1023) NOT NULL COMMENT '描述' ")
    private String description;

    @Column(columnDefinition = "VARCHAR(1023) NOT NULL COMMENT '备注' ")
    private String remark;

    @Convert(converter = MapStringConverter.class)
    @Column(columnDefinition = "JSON COMMENT '请求参数' ")
    private Map<String, String> reqParams;

    @Convert(converter = MapStringConverter.class)
    @Column(columnDefinition = "JSON COMMENT '响应参数' ")
    private Map<String, String> respParams;
}
