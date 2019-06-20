package com.hottop.core.model.community;

<<<<<<< HEAD
import com.hottop.core.model.community.Tag.TagType;
=======
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tag.class)
public abstract class Tag_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<Tag, String> tag;
<<<<<<< HEAD
	public static volatile SingularAttribute<Tag, TagType> type;

	public static final String TAG = "tag";
	public static final String TYPE = "type";
=======
	public static volatile SingularAttribute<Tag, Long> communityId;

	public static final String TAG = "tag";
	public static final String COMMUNITY_ID = "communityId";
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

}

