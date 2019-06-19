package com.hottop.core.model.commerce;

import com.hottop.core.model.zpoj.bean.Media;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CommerceBrand.class)
public abstract class CommerceBrand_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<CommerceBrand, String> country;
	public static volatile SingularAttribute<CommerceBrand, String> name;
	public static volatile SingularAttribute<CommerceBrand, String> description;
	public static volatile SingularAttribute<CommerceBrand, Media> avatar;
	public static volatile SingularAttribute<CommerceBrand, Long> categoryId;

	public static final String COUNTRY = "country";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String AVATAR = "avatar";
	public static final String CATEGORY_ID = "categoryId";

}

