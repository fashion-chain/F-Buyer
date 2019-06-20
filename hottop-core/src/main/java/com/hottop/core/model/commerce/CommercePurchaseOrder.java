package com.hottop.core.model.commerce;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.feature.status.IStatusFlow;
import com.hottop.core.feature.status.Status;
import com.hottop.core.feature.status.StatusFactory;
import com.hottop.core.feature.status.StatusStatusTracker;
import com.hottop.core.model.commerce.enums.EPurchaseOrderState;
import com.hottop.core.model.merchant.enums.ETradeProvider;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.converter.StatusTrackerConverter;
import com.hottop.core.request.argument.filter.EFilterOperator;
import com.hottop.core.request.argument.filter.EFilterWidgetType;
import com.hottop.core.request.argument.filter.api.FieldIndicator;
import com.hottop.core.request.argument.filter.api.FilterFieldIndicator;
import com.hottop.core.request.argument.filter.api.FilterFieldWidget;
import com.hottop.core.request.argument.filter.api.ProgramInterfaceIndicator;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Data
@Entity
public class CommercePurchaseOrder extends EntityBase {

    /*用户信息*/
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

    @Transient
    private String statusShowName;

    @Transient
    private Map<String,String> statusMap;

    /*支付信息*/
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

    @Column(columnDefinition = "VARCHAR(63) COMMENT '订单号'")
    private String tradeNo;

    @Column(columnDefinition = "VARCHAR(63) COMMENT '支付订单号' ")
    private String payTradeNo;

    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '发货时间'")
    private Date deliveryTime;

    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '收货时间'")
    private Date receiveTime;

    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '交易关闭时间'")
    private Date closeTime;

//
//    /*退款信息*/
//
//    private Date refundTime;
//
//    private Long refundAmount;
//
//    private String refundTradeNo;
//
//    private String refundReason;

    //订单状态初始化
    public static void initStatus() throws Exception {
        IStatusFlow<Class<?>, Status> purchaseOrderFlow = StatusFactory.registerClazz(CommercePurchaseOrder.class, "po");
        Status poOrderPrepay = StatusFactory.newStatus(CommercePurchaseOrder.class, "order", EPurchaseOrderState.prePay.name());
        Status poOrderPreDeliver = StatusFactory.newStatus(CommercePurchaseOrder.class, "order", EPurchaseOrderState.preDeliver.name());
        Status poOrderPreReceive = StatusFactory.newStatus(CommercePurchaseOrder.class, "order", EPurchaseOrderState.preReceive.name());
        Status poOrderFinish = StatusFactory.newStatus(CommercePurchaseOrder.class, "order", EPurchaseOrderState.orderFinish.name());
        Status poOrderClose = StatusFactory.newStatus(CommercePurchaseOrder.class, "order", EPurchaseOrderState.orderClose.name());
        purchaseOrderFlow.registerStatuses(poOrderPrepay, poOrderPreDeliver, poOrderPreReceive, poOrderFinish, poOrderClose);
        /**
         * poOrderPrepay
         * 订单待支付 ->
         *      待发货
         *      订单关闭
         */
        purchaseOrderFlow.getTrackerByStatus(poOrderPrepay.status())
                .registerEvent(EPurchaseOrderState.preDeliver, poOrderPreDeliver)
                .registerEvent(EPurchaseOrderState.orderClose, poOrderClose);
        /**
         * poOrderPreDeliver
         * 订单待发货 ->
         *      待收货
         *      订单完成
         */
        purchaseOrderFlow.getTrackerByStatus(poOrderPreDeliver.status())
                .registerEvent(EPurchaseOrderState.preReceive, poOrderPreReceive)
                .registerEvent(EPurchaseOrderState.orderFinish, poOrderFinish);
        /**
         * poOrderPreReceive
         * 订单待收货 ->
         *      订单完成
         */
        purchaseOrderFlow.getTrackerByStatus(poOrderPreReceive.status())
                .registerEvent(EPurchaseOrderState.orderFinish, poOrderFinish);
        /**
         * poOrderFinish
         * 订单待收货 ->
         *      订单完成
         */
        purchaseOrderFlow.getTrackerByStatus(poOrderFinish.status())
                .registerEvent(EPurchaseOrderState.orderClose, poOrderClose);
    }

    public static ProgramInterfaceIndicator getProgramInterfaceIndicator() throws Exception {
        return ProgramInterfaceIndicator.ProgramInterfaceIndicatorBuilder.init(CommercePurchaseOrder.class)
                //字段指示器
                .fieldIndicators(
                        FieldIndicator.FieldIndicatorBuilder.field(CommercePurchaseOrder_.PAY_TRADE_NO,
                                BaseConfiguration.getMessage("commerce.purchase.payTradeNo"),
                                FilterFieldWidget.simpleInput()
                        ).create(),
                        FieldIndicator.FieldIndicatorBuilder.field(CommercePurchaseOrder_.CREATE_TIME,
                                BaseConfiguration.getMessage("commerce.purchase.createTime"),
                                FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.date).create()
                        ).create(),
                        FieldIndicator.FieldIndicatorBuilder.field(CommercePurchaseOrder_.TEL,
                                BaseConfiguration.getMessage("commerce.purchase.tel"),
                                FilterFieldWidget.simpleInput()
                        ).create(),
                        FieldIndicator.FieldIndicatorBuilder.field(CommercePurchaseOrder_.ADDRESS,
                                BaseConfiguration.getMessage("commerce.purchase.address"),
                                FilterFieldWidget.simpleInput()
                        ).create(),
                        FieldIndicator.FieldIndicatorBuilder.field(CommercePurchaseOrder_.USERNAME,
                                BaseConfiguration.getMessage("commerce.purchase.username"),
                                FilterFieldWidget.simpleInput()
                        ).create(),
                        FieldIndicator.FieldIndicatorBuilder.field("status",
                                BaseConfiguration.getMessage("commerce.purchase.status"),
                                FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select).create()
                        ).create(),
                        FieldIndicator.FieldIndicatorBuilder.field(CommercePurchaseOrder_.AMOUNT,
                                BaseConfiguration.getMessage("commerce.purchase.amount"),
                                FilterFieldWidget.simpleInput()
                        ).create(),
                        FieldIndicator.FieldIndicatorBuilder.field(CommercePurchaseOrder_.PAY_AMOUNT,
                                BaseConfiguration.getMessage("commerce.purchase.payAmount"),
                                FilterFieldWidget.simpleInput()
                        ).create()
                )
                //.requiredFields(Permission_.PERMISSION_NAME, Permission_.DESCRIPTION, Permission_.STATE)
                //.sortableFields(Permission_.NAME)
                .filterFieldIndicators(
                        FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(CommercePurchaseOrder_.PAY_TRADE_NO,
                                BaseConfiguration.getMessage("commerce.purchase.payTradeNo"),
                                EFilterOperator.equal)
                                .widget(FilterFieldWidget.simpleInput())
                                .create(),
                        FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(CommercePurchaseOrder_.CREATE_TIME,
                                BaseConfiguration.getMessage("commerce.purchase.createTime"),
                                EFilterOperator.greaterEqualThan, EFilterOperator.lessEqualThan)
                                .widget(
                                        FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.durationDate).create()
                                )
                                .create(),
                        FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField("status",
                                BaseConfiguration.getMessage("commerce.purchase.status"),
                                EFilterOperator.equal)
                                .widget(
                                        FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select)
                                                .uri("/purchaseOrder/statusMap", RequestMethod.GET)
                                                .create()
                                )
                                .create()
                )
                .create();
    }

}
