package com.hottop.core.model.community;

import java.util.ArrayList;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Community.class)
public abstract class Community_ extends com.hottop.core.model.community.Container_ {

	public static volatile SingularAttribute<Community, ArrayList> moderators;
	public static volatile SingularAttribute<Community, ArrayList> tags;

	public static final String MODERATORS = "moderators";
	public static final String TAGS = "tags";

}

