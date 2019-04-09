package com.hottop.core.model.community;

import java.util.ArrayList;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Container.class)
public abstract class Container_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<Container, String> subtitle;
	public static volatile SingularAttribute<Container, String> subscriberCount;
	public static volatile SingularAttribute<Container, ArrayList> rules;
	public static volatile SingularAttribute<Container, String> title;
	public static volatile SingularAttribute<Container, String> slug;

	public static final String SUBTITLE = "subtitle";
	public static final String SUBSCRIBER_COUNT = "subscriberCount";
	public static final String RULES = "rules";
	public static final String TITLE = "title";
	public static final String SLUG = "slug";

}

