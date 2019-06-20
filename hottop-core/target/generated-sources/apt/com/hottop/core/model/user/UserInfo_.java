package com.hottop.core.model.user;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserInfo.class)
public abstract class UserInfo_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<UserInfo, String> realName;
	public static volatile SingularAttribute<UserInfo, String> gender;
<<<<<<< HEAD
=======
	public static volatile SingularAttribute<UserInfo, Integer> level;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
	public static volatile SingularAttribute<UserInfo, String> identityCardType;
	public static volatile SingularAttribute<UserInfo, String> identityCardNo;
	public static volatile SingularAttribute<UserInfo, Long> userId;

	public static final String REAL_NAME = "realName";
	public static final String GENDER = "gender";
<<<<<<< HEAD
=======
	public static final String LEVEL = "level";
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
	public static final String IDENTITY_CARD_TYPE = "identityCardType";
	public static final String IDENTITY_CARD_NO = "identityCardNo";
	public static final String USER_ID = "userId";

}

