package com.hottop.core.model.commerce;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CommercePurchaseOrderSku.class)
public abstract class CommercePurchaseOrderSku_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<CommercePurchaseOrderSku, Long> amount;
	public static volatile SingularAttribute<CommercePurchaseOrderSku, Long> payAmount;
	public static volatile SingularAttribute<CommercePurchaseOrderSku, Integer> quantity;
	public static volatile SingularAttribute<CommercePurchaseOrderSku, Long> purchaseOrderId;
	public static volatile SingularAttribute<CommercePurchaseOrderSku, Long> spuId;
	public static volatile SingularAttribute<CommercePurchaseOrderSku, Long> userId;
	public static volatile SingularAttribute<CommercePurchaseOrderSku, Long> skuId;

	public static final String AMOUNT = "amount";
	public static final String PAY_AMOUNT = "payAmount";
	public static final String QUANTITY = "quantity";
	public static final String PURCHASE_ORDER_ID = "purchaseOrderId";
	public static final String SPU_ID = "spuId";
	public static final String USER_ID = "userId";
	public static final String SKU_ID = "skuId";

}

