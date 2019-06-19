package com.hottop.core.model.community;

import com.hottop.core.model.community.bean.ECommunityType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Community.class)
public abstract class Community_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<Community, ECommunityType> communityType;
	public static volatile SingularAttribute<Community, String> description;
	public static volatile SingularAttribute<Community, String> title;
	public static volatile SingularAttribute<Community, Long> pageId;

	public static final String COMMUNITY_TYPE = "communityType";
	public static final String DESCRIPTION = "description";
	public static final String TITLE = "title";
	public static final String PAGE_ID = "pageId";

}

