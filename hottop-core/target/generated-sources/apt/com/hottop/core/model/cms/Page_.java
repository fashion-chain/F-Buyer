package com.hottop.core.model.cms;

import com.hottop.core.model.cms.bean.PageContent;
import com.hottop.core.model.cms.bean.component.bean.DataDecorator;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Page.class)
public abstract class Page_ extends com.hottop.core.model.zpoj.EntityBase_ {

	public static volatile SingularAttribute<Page, String> name;
	public static volatile SingularAttribute<Page, PageContent> pageContent;
	public static volatile SingularAttribute<Page, DataDecorator> dataDecorator;

	public static final String NAME = "name";
	public static final String PAGE_CONTENT = "pageContent";
	public static final String DATA_DECORATOR = "dataDecorator";

}

