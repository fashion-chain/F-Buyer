package com.hottop.core.model.commerce;

import com.hottop.core.model.commerce.enums.EAttributeType;
import java.util.ArrayList;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CommerceAttribute.class)
public abstract class CommerceAttribute_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<CommerceAttribute, String> name;
	public static volatile SingularAttribute<CommerceAttribute, ArrayList> recommendationValues;
	public static volatile SingularAttribute<CommerceAttribute, EAttributeType> type;

	public static final String NAME = "name";
	public static final String RECOMMENDATION_VALUES = "recommendationValues";
	public static final String TYPE = "type";

}

