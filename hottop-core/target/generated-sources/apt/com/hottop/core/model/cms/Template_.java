package com.hottop.core.model.cms;

import com.hottop.core.model.cms.bean.TemplateContent;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Template.class)
public abstract class Template_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<Template, String> name;
	public static volatile SingularAttribute<Template, TemplateContent> content;

	public static final String NAME = "name";
	public static final String CONTENT = "content";

}

