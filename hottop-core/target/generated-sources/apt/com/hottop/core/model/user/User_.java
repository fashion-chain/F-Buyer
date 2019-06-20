package com.hottop.core.model.user;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<User, String> password;
<<<<<<< HEAD
	public static volatile SingularAttribute<User, Long> modifyId;
=======
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
	public static volatile ListAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, Date> loginLastTime;
	public static volatile SingularAttribute<User, String> tel;
	public static volatile SingularAttribute<User, String> remark;
	public static volatile SingularAttribute<User, String> state;
<<<<<<< HEAD
	public static volatile SingularAttribute<User, Long> createrId;
=======
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> username;

	public static final String PASSWORD = "password";
<<<<<<< HEAD
	public static final String MODIFY_ID = "modifyId";
=======
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
	public static final String ROLES = "roles";
	public static final String LOGIN_LAST_TIME = "loginLastTime";
	public static final String TEL = "tel";
	public static final String REMARK = "remark";
	public static final String STATE = "state";
<<<<<<< HEAD
	public static final String CREATER_ID = "createrId";
=======
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";

}

