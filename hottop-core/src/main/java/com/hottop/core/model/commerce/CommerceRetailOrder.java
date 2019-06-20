package com.hottop.core.model.commerce;

import com.hottop.core.feature.status.IStatusFlow;
import com.hottop.core.feature.status.Status;
import com.hottop.core.feature.status.StatusFactory;
import com.hottop.core.feature.status.StatusStatusTracker;
import com.hottop.core.model.commerce.enums.EPurchaseOrderState;
import com.hottop.core.model.merchant.enums.ETradeProvider;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.converter.StatusTrackerConverter;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class CommerceRetailOrder extends EntityBase {

    @Column(columnDefinition = "INT(11) DEFAULT NULL COMMENT '用户id'")
    private Long userId;

    @Column(columnDefinition = "VARCHAR(20) DEFAULT '' COMMENT '电话'")
    private String tel;

    @Column(columnDefinition = "VARCHAR(200) DEFAULT '' COMMENT '地址'")
    private String address;

    @Column(columnDefinition = "VARCHAR(80) DEFAULT '' COMMENT '用户名'")
    private String username;

    @Column(columnDefinition = "VARCHAR(31) NOT NULL COMMENT '订单状态' ")
    @Convert(converter = StatusTrackerConverter.class)
    private StatusStatusTracker status;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '订单金额' ")
    private Long amount;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '支付金额' ")
    private Long payAmount;

    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '支付时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payTime;

    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '支付方式' ")
    @Enumerated(EnumType.STRING)
    private ETradeProvider payMeans;

    @Column(columnDefinition = "VARCHAR(63) COMMENT '支付订单号' ")
    private String payTradeNo;

    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '发货时间'")
    private Date deliveryTime;

    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '收货时间'")
    private Date receiveTime;

    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '交易关闭时间'")
    private Date closeTime;

    /*退款信息*/
    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '退款时间'")
    private Date refundTime;

    @Column(columnDefinition = "INT(11) DEFAULT NULL COMMENT '退款金额'")
    private Long refundAmount;

    @Column(columnDefinition = "VARCHAR(63) COMMENT '退款流水号' ")
    private String refundTradeNo;

    @Column(columnDefinition = "VARCHAR(127) COMMENT '退款原因' ")
    private String refundReason;
}
