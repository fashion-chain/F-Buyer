package com.hottop.core.model.commerce;

import com.hottop.core.model.commerce.enums.EPurchaseOrderState;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CommercePurchaseOrder.class)
public abstract class CommercePurchaseOrder_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<CommercePurchaseOrder, String> payMeans;
	public static volatile SingularAttribute<CommercePurchaseOrder, Long> amount;
	public static volatile SingularAttribute<CommercePurchaseOrder, String> address;
	public static volatile SingularAttribute<CommercePurchaseOrder, Date> deliveryTime;
	public static volatile SingularAttribute<CommercePurchaseOrder, Date> payTime;
	public static volatile SingularAttribute<CommercePurchaseOrder, String> payTradeNo;
	public static volatile SingularAttribute<CommercePurchaseOrder, Long> userId;
	public static volatile SingularAttribute<CommercePurchaseOrder, Date> receiveTime;
	public static volatile SingularAttribute<CommercePurchaseOrder, Long> payAmount;
	public static volatile SingularAttribute<CommercePurchaseOrder, String> phone;
	public static volatile SingularAttribute<CommercePurchaseOrder, Date> closeTime;
	public static volatile SingularAttribute<CommercePurchaseOrder, EPurchaseOrderState> state;
	public static volatile SingularAttribute<CommercePurchaseOrder, String> username;

	public static final String PAY_MEANS = "payMeans";
	public static final String AMOUNT = "amount";
	public static final String ADDRESS = "address";
	public static final String DELIVERY_TIME = "deliveryTime";
	public static final String PAY_TIME = "payTime";
	public static final String PAY_TRADE_NO = "payTradeNo";
	public static final String USER_ID = "userId";
	public static final String RECEIVE_TIME = "receiveTime";
	public static final String PAY_AMOUNT = "payAmount";
	public static final String PHONE = "phone";
	public static final String CLOSE_TIME = "closeTime";
	public static final String STATE = "state";
	public static final String USERNAME = "username";

}

