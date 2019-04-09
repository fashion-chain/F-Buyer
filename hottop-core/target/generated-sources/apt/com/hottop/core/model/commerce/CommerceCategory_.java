package com.hottop.core.model.commerce;

import java.util.ArrayList;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CommerceCategory.class)
public abstract class CommerceCategory_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<CommerceCategory, String> name;
	public static volatile SingularAttribute<CommerceCategory, ArrayList> attributes;
	public static volatile SingularAttribute<CommerceCategory, ArrayList> specifications;
	public static volatile SingularAttribute<CommerceCategory, Long> parentId;

	public static final String NAME = "name";
	public static final String ATTRIBUTES = "attributes";
	public static final String SPECIFICATIONS = "specifications";
	public static final String PARENT_ID = "parentId";

}

