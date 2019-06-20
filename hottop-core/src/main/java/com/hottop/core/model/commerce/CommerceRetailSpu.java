package com.hottop.core.model.commerce;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Data
public class CommerceRetailSpu extends EntityBase {

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '商品Id' ")
    private Long spuId;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT 'appId' ")
    private Long appId;

    @Column(columnDefinition = "VARCHAR(63) DEFAULT '' COMMENT '市场价' ")
    private String marketPrice;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL DEFAULT '' COMMENT '零售备注' ")
    private String remark;

    @Transient
    private List<CommerceRetailSku> skus;

    //零售商品，所属的采购单ID
    private Long purchaseOrderId;
}
