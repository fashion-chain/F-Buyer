package com.hottop.core.model.zpoj;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EntityBase.class)
public abstract class EntityBase_ {

	public static volatile SingularAttribute<EntityBase, Date> createTime;
	public static volatile SingularAttribute<EntityBase, Date> deleteTime;
	public static volatile SingularAttribute<EntityBase, Date> updateTime;
	public static volatile SingularAttribute<EntityBase, Long> id;

	public static final String CREATE_TIME = "createTime";
	public static final String DELETE_TIME = "deleteTime";
	public static final String UPDATE_TIME = "updateTime";
	public static final String ID = "id";

}

