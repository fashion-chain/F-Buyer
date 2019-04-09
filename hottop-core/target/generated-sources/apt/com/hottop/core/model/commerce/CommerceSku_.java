package com.hottop.core.model.commerce;

import com.hottop.core.model.zpoj.commerce.bean.CommerceSkuSpecificationIndicator;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CommerceSku.class)
public abstract class CommerceSku_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<CommerceSku, Integer> purchaseUnit;
	public static volatile SingularAttribute<CommerceSku, Long> marketPrice;
	public static volatile SingularAttribute<CommerceSku, Long> salePrice;
	public static volatile SingularAttribute<CommerceSku, String> subtitle;
	public static volatile SingularAttribute<CommerceSku, Long> spuId;
	public static volatile SingularAttribute<CommerceSku, Integer> inventory;
	public static volatile SingularAttribute<CommerceSku, CommerceSkuSpecificationIndicator> indicators;

	public static final String PURCHASE_UNIT = "purchaseUnit";
	public static final String MARKET_PRICE = "marketPrice";
	public static final String SALE_PRICE = "salePrice";
	public static final String SUBTITLE = "subtitle";
	public static final String SPU_ID = "spuId";
	public static final String INVENTORY = "inventory";
	public static final String INDICATORS = "indicators";

}

