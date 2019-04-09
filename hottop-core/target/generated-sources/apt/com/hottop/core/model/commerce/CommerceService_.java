package com.hottop.core.model.commerce;

import com.hottop.core.model.commerce.enums.EServiceType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CommerceService.class)
public abstract class CommerceService_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<CommerceService, String> name;
	public static volatile SingularAttribute<CommerceService, String> description;
	public static volatile SingularAttribute<CommerceService, EServiceType> type;

	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String TYPE = "type";

}

