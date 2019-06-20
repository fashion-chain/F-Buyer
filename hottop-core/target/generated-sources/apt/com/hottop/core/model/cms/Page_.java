package com.hottop.core.model.cms;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.HashMap;
=======
import com.hottop.core.model.cms.bean.PageContent;
import com.hottop.core.model.cms.bean.component.bean.DataDecorator;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Page.class)
public abstract class Page_ extends com.hottop.core.model.zpoj.EntityBase_ {

<<<<<<< HEAD
	public static volatile SingularAttribute<Page, ArrayList> pageOrder;
	public static volatile SingularAttribute<Page, String> name;
	public static volatile SingularAttribute<Page, Integer> state;
	public static volatile SingularAttribute<Page, Long> templateId;
	public static volatile SingularAttribute<Page, Integer> version;
	public static volatile SingularAttribute<Page, HashMap> content;

	public static final String PAGE_ORDER = "pageOrder";
	public static final String NAME = "name";
	public static final String STATE = "state";
	public static final String TEMPLATE_ID = "templateId";
	public static final String VERSION = "version";
	public static final String CONTENT = "content";
=======
	public static volatile SingularAttribute<Page, String> name;
	public static volatile SingularAttribute<Page, PageContent> pageContent;
	public static volatile SingularAttribute<Page, DataDecorator> dataDecorator;

	public static final String NAME = "name";
	public static final String PAGE_CONTENT = "pageContent";
	public static final String DATA_DECORATOR = "dataDecorator";
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

}

