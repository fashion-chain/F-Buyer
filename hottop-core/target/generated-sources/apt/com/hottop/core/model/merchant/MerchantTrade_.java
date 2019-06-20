package com.hottop.core.model.merchant;

import com.hottop.core.model.merchant.enums.ECurrency;
import com.hottop.core.model.merchant.enums.ETradeOperate;
import com.hottop.core.model.merchant.enums.ETradeProvider;
<<<<<<< HEAD
import com.hottop.core.model.merchant.enums.ETradeStatus;
import javax.annotation.Generated;
=======
import com.hottop.core.model.merchant.enums.ETradeSource;
import javax.annotation.Generated;
import javax.persistence.metamodel.MapAttribute;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MerchantTrade.class)
public abstract class MerchantTrade_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<MerchantTrade, Long> amount;
	public static volatile SingularAttribute<MerchantTrade, String> tradeNo;
<<<<<<< HEAD
	public static volatile SingularAttribute<MerchantTrade, String> refundNo;
	public static volatile SingularAttribute<MerchantTrade, ETradeProvider> provider;
	public static volatile SingularAttribute<MerchantTrade, String> outTradeNo;
	public static volatile SingularAttribute<MerchantTrade, String> description;
	public static volatile SingularAttribute<MerchantTrade, ECurrency> currency;
	public static volatile SingularAttribute<MerchantTrade, String> tradeToken;
	public static volatile SingularAttribute<MerchantTrade, String> remark;
	public static volatile SingularAttribute<MerchantTrade, ETradeOperate> tradeOperate;
	public static volatile SingularAttribute<MerchantTrade, ETradeStatus> status;

	public static final String AMOUNT = "amount";
	public static final String TRADE_NO = "tradeNo";
	public static final String REFUND_NO = "refundNo";
	public static final String PROVIDER = "provider";
	public static final String OUT_TRADE_NO = "outTradeNo";
	public static final String DESCRIPTION = "description";
	public static final String CURRENCY = "currency";
	public static final String TRADE_TOKEN = "tradeToken";
	public static final String REMARK = "remark";
	public static final String TRADE_OPERATE = "tradeOperate";
	public static final String STATUS = "status";
=======
	public static volatile MapAttribute<MerchantTrade, String, String> reqParams;
	public static volatile SingularAttribute<MerchantTrade, ETradeProvider> tradeProvider;
	public static volatile SingularAttribute<MerchantTrade, String> subject;
	public static volatile SingularAttribute<MerchantTrade, String> description;
	public static volatile SingularAttribute<MerchantTrade, String> remark;
	public static volatile SingularAttribute<MerchantTrade, ETradeOperate> tradeOperate;
	public static volatile MapAttribute<MerchantTrade, String, String> respParams;
	public static volatile SingularAttribute<MerchantTrade, String> outTradeNo;
	public static volatile SingularAttribute<MerchantTrade, ECurrency> currency;
	public static volatile SingularAttribute<MerchantTrade, String> tradeToken;
	public static volatile SingularAttribute<MerchantTrade, ETradeSource> tradeSource;

	public static final String AMOUNT = "amount";
	public static final String TRADE_NO = "tradeNo";
	public static final String REQ_PARAMS = "reqParams";
	public static final String TRADE_PROVIDER = "tradeProvider";
	public static final String SUBJECT = "subject";
	public static final String DESCRIPTION = "description";
	public static final String REMARK = "remark";
	public static final String TRADE_OPERATE = "tradeOperate";
	public static final String RESP_PARAMS = "respParams";
	public static final String OUT_TRADE_NO = "outTradeNo";
	public static final String CURRENCY = "currency";
	public static final String TRADE_TOKEN = "tradeToken";
	public static final String TRADE_SOURCE = "tradeSource";
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

}

