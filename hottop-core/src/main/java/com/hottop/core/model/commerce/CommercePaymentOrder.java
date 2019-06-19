package com.hottop.core.model.commerce;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

//代理，经销商缴费表
@Entity
@Data
public class CommercePaymentOrder extends EntityBase {

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '用户ID' ")
    private Long userId;

    @Column(columnDefinition = "INT(11) NOT NULL DEFAULT 0 COMMENT '缴费金额（单位：分）' ")
    private Long amount;


}
