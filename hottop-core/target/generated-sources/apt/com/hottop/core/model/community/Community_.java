package com.hottop.core.model.community;

<<<<<<< HEAD
import java.util.ArrayList;
=======
import com.hottop.core.model.community.bean.ECommunityType;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Community.class)
<<<<<<< HEAD
public abstract class Community_ extends com.hottop.core.model.community.Container_ {

	public static volatile SingularAttribute<Community, ArrayList> moderators;
	public static volatile SingularAttribute<Community, ArrayList> tags;

	public static final String MODERATORS = "moderators";
	public static final String TAGS = "tags";
=======
public abstract class Community_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<Community, ECommunityType> communityType;
	public static volatile SingularAttribute<Community, String> description;
	public static volatile SingularAttribute<Community, String> title;
	public static volatile SingularAttribute<Community, Long> pageId;

	public static final String COMMUNITY_TYPE = "communityType";
	public static final String DESCRIPTION = "description";
	public static final String TITLE = "title";
	public static final String PAGE_ID = "pageId";
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

}

