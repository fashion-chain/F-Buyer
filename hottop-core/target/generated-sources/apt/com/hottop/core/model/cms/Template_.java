package com.hottop.core.model.cms;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.HashMap;
=======
import com.hottop.core.model.cms.bean.TemplateContent;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Template.class)
public abstract class Template_ extends com.hottop.core.model.zpoj.EntityBase_ {

<<<<<<< HEAD
	public static volatile SingularAttribute<Template, ArrayList> templateOrder;
	public static volatile SingularAttribute<Template, HashMap> templates;
	public static volatile SingularAttribute<Template, String> name;

	public static final String TEMPLATE_ORDER = "templateOrder";
	public static final String TEMPLATES = "templates";
	public static final String NAME = "name";
=======
	public static volatile SingularAttribute<Template, String> name;
	public static volatile SingularAttribute<Template, TemplateContent> content;

	public static final String NAME = "name";
	public static final String CONTENT = "content";
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

}

