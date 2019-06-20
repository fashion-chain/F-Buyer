package com.hottop.core.model.commerce;

import java.util.ArrayList;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CommerceSpu.class)
public abstract class CommerceSpu_ extends com.hottop.core.model.zpoj.EntityBase_ {

<<<<<<< HEAD
	public static volatile SingularAttribute<CommerceSpu, String> marketPrice;
	public static volatile SingularAttribute<CommerceSpu, ArrayList> comparison;
	public static volatile SingularAttribute<CommerceSpu, String> salePrice;
	public static volatile SingularAttribute<CommerceSpu, ArrayList> services;
	public static volatile SingularAttribute<CommerceSpu, String> title;
	public static volatile SingularAttribute<CommerceSpu, Integer> inventory;
	public static volatile SingularAttribute<CommerceSpu, ArrayList> carousel;
	public static volatile SingularAttribute<CommerceSpu, ArrayList> specifications;
	public static volatile SingularAttribute<CommerceSpu, Integer> purchaseUnit;
	public static volatile SingularAttribute<CommerceSpu, Long> brandId;
	public static volatile SingularAttribute<CommerceSpu, ArrayList> attributes;
	public static volatile SingularAttribute<CommerceSpu, Long> categoryId;
	public static volatile SingularAttribute<CommerceSpu, ArrayList> info;

	public static final String MARKET_PRICE = "marketPrice";
	public static final String COMPARISON = "comparison";
	public static final String SALE_PRICE = "salePrice";
	public static final String SERVICES = "services";
	public static final String TITLE = "title";
	public static final String INVENTORY = "inventory";
	public static final String CAROUSEL = "carousel";
	public static final String SPECIFICATIONS = "specifications";
	public static final String PURCHASE_UNIT = "purchaseUnit";
	public static final String BRAND_ID = "brandId";
	public static final String ATTRIBUTES = "attributes";
	public static final String CATEGORY_ID = "categoryId";
=======
	public static volatile SingularAttribute<CommerceSpu, Integer> purchaseUnit;
	public static volatile SingularAttribute<CommerceSpu, String> marketPrice;
	public static volatile SingularAttribute<CommerceSpu, ArrayList> comparison;
	public static volatile SingularAttribute<CommerceSpu, Long> brandId;
	public static volatile SingularAttribute<CommerceSpu, ArrayList> attributes;
	public static volatile SingularAttribute<CommerceSpu, ArrayList> services;
	public static volatile SingularAttribute<CommerceSpu, String> title;
	public static volatile SingularAttribute<CommerceSpu, ArrayList> carousel;
	public static volatile SingularAttribute<CommerceSpu, ArrayList> specifications;
	public static volatile SingularAttribute<CommerceSpu, Long> categoryId;
	public static volatile SingularAttribute<CommerceSpu, String> status;
	public static volatile SingularAttribute<CommerceSpu, ArrayList> info;

	public static final String PURCHASE_UNIT = "purchaseUnit";
	public static final String MARKET_PRICE = "marketPrice";
	public static final String COMPARISON = "comparison";
	public static final String BRAND_ID = "brandId";
	public static final String ATTRIBUTES = "attributes";
	public static final String SERVICES = "services";
	public static final String TITLE = "title";
	public static final String CAROUSEL = "carousel";
	public static final String SPECIFICATIONS = "specifications";
	public static final String CATEGORY_ID = "categoryId";
	public static final String STATUS = "status";
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
	public static final String INFO = "info";

}

