package com.hottop.core.model.commerce;

import com.hottop.core.model.merchant.enums.ETradeProvider;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CommercePurchaseOrder.class)
public abstract class CommercePurchaseOrder_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<CommercePurchaseOrder, ETradeProvider> payMeans;
	public static volatile SingularAttribute<CommercePurchaseOrder, Long> amount;
	public static volatile SingularAttribute<CommercePurchaseOrder, Long> agentId;
	public static volatile SingularAttribute<CommercePurchaseOrder, String> address;
	public static volatile SingularAttribute<CommercePurchaseOrder, String> tradeNo;
	public static volatile SingularAttribute<CommercePurchaseOrder, Date> deliveryTime;
	public static volatile SingularAttribute<CommercePurchaseOrder, Date> payTime;
	public static volatile SingularAttribute<CommercePurchaseOrder, Long> distributorId;
	public static volatile SingularAttribute<CommercePurchaseOrder, String> payTradeNo;
	public static volatile SingularAttribute<CommercePurchaseOrder, Long> userId;
	public static volatile SingularAttribute<CommercePurchaseOrder, Long> distributorRebate;
	public static volatile SingularAttribute<CommercePurchaseOrder, Date> receiveTime;
	public static volatile SingularAttribute<CommercePurchaseOrder, Long> payAmount;
	public static volatile SingularAttribute<CommercePurchaseOrder, Date> closeTime;
	public static volatile SingularAttribute<CommercePurchaseOrder, String> tel;
	public static volatile SingularAttribute<CommercePurchaseOrder, Long> agentRebate;
	public static volatile SingularAttribute<CommercePurchaseOrder, String> username;

	public static final String PAY_MEANS = "payMeans";
	public static final String AMOUNT = "amount";
	public static final String AGENT_ID = "agentId";
	public static final String ADDRESS = "address";
	public static final String TRADE_NO = "tradeNo";
	public static final String DELIVERY_TIME = "deliveryTime";
	public static final String PAY_TIME = "payTime";
	public static final String DISTRIBUTOR_ID = "distributorId";
	public static final String PAY_TRADE_NO = "payTradeNo";
	public static final String USER_ID = "userId";
	public static final String DISTRIBUTOR_REBATE = "distributorRebate";
	public static final String RECEIVE_TIME = "receiveTime";
	public static final String PAY_AMOUNT = "payAmount";
	public static final String CLOSE_TIME = "closeTime";
	public static final String TEL = "tel";
	public static final String AGENT_REBATE = "agentRebate";
	public static final String USERNAME = "username";

}

