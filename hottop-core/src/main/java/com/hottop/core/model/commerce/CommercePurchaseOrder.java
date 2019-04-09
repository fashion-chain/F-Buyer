package com.hottop.core.model.commerce;

import com.hottop.core.model.commerce.enums.EPurchaseOrderState;
import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class CommercePurchaseOrder extends EntityBase {

    /*用户信息*/

    private Long userId;

    private String phone;

    private String address;

    private String username;

    private EPurchaseOrderState state;


    /*支付信息*/

    private Long amount;

    private Long payAmount;

    private Date payTime;

    private String payMeans;

    private String payTradeNo;


    private Date deliveryTime;

    private Date receiveTime;

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

}
