package com.hottop.core.model.user;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile ListAttribute<Role, Permission> permissions;
	public static volatile SingularAttribute<Role, String> roleName;
	public static volatile SingularAttribute<Role, String> remark;
	public static volatile SingularAttribute<Role, String> state;

	public static final String PERMISSIONS = "permissions";
	public static final String ROLE_NAME = "roleName";
	public static final String REMARK = "remark";
	public static final String STATE = "state";

}

