package com.hottop.core.model.user;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Permission.class)
public abstract class Permission_ extends com.hottop.core.model.zpoj.EntityBase_ {

<<<<<<< HEAD
	public static volatile SingularAttribute<Permission, String> modifyId;
	public static volatile SingularAttribute<Permission, String> description;
	public static volatile SingularAttribute<Permission, String> pid;
	public static volatile SingularAttribute<Permission, String> state;
	public static volatile SingularAttribute<Permission, String> createrId;
	public static volatile SingularAttribute<Permission, String> url;
	public static volatile SingularAttribute<Permission, String> permissionName;

	public static final String MODIFY_ID = "modifyId";
	public static final String DESCRIPTION = "description";
	public static final String PID = "pid";
	public static final String STATE = "state";
	public static final String CREATER_ID = "createrId";
=======
	public static volatile SingularAttribute<Permission, String> method;
	public static volatile SingularAttribute<Permission, String> description;
	public static volatile SingularAttribute<Permission, Long> pid;
	public static volatile SingularAttribute<Permission, String> state;
	public static volatile SingularAttribute<Permission, String> url;
	public static volatile SingularAttribute<Permission, String> permissionName;

	public static final String METHOD = "method";
	public static final String DESCRIPTION = "description";
	public static final String PID = "pid";
	public static final String STATE = "state";
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
	public static final String URL = "url";
	public static final String PERMISSION_NAME = "permissionName";

}

