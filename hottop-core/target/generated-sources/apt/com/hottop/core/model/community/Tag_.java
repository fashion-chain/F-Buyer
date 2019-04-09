package com.hottop.core.model.community;

import com.hottop.core.model.community.Tag.TagType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tag.class)
public abstract class Tag_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<Tag, String> tag;
	public static volatile SingularAttribute<Tag, TagType> type;

	public static final String TAG = "tag";
	public static final String TYPE = "type";

}

