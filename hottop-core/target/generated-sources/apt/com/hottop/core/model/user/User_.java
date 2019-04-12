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
	public static volatile SingularAttribute<User, String> salt;
	public static volatile SingularAttribute<User, Long> modifyId;
	public static volatile ListAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, Long> creatorId;
	public static volatile SingularAttribute<User, Date> loginLastTime;
	public static volatile SingularAttribute<User, String> tel;
	public static volatile SingularAttribute<User, String> remark;
	public static volatile SingularAttribute<User, String> state;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> username;

	public static final String PASSWORD = "password";
	public static final String SALT = "salt";
	public static final String MODIFY_ID = "modifyId";
	public static final String ROLES = "roles";
	public static final String CREATOR_ID = "creatorId";
	public static final String LOGIN_LAST_TIME = "loginLastTime";
	public static final String TEL = "tel";
	public static final String REMARK = "remark";
	public static final String STATE = "state";
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";

}

