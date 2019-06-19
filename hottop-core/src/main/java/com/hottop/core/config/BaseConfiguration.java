package com.hottop.core.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hottop.core.BaseConstant;
import com.hottop.core.feature.status.*;
import com.hottop.core.feature.type.TypeIndicator;
import com.hottop.core.model.cms.bean.action.ActionBase;
import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.bean.ComponentDecorator;
import com.hottop.core.model.cms.bean.extractor.ExtractorFactory;
import com.hottop.core.model.cms.bean.extractor.ICmsExtractor;
import com.hottop.core.model.commerce.*;
import com.hottop.core.model.doll.Doll;
import com.hottop.core.model.doll.Doll_;
import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.model.user.Capability;
import com.hottop.core.model.user.ModuleBase;
import com.hottop.core.model.zpoj.adapter.EnumAdapterFactory;
import com.hottop.core.model.zpoj.adapter.SpringfoxJsonToGsonAdapter;
import com.hottop.core.model.zpoj.adapter.serializer.*;
import com.hottop.core.model.zpoj.bean.Media;
import com.hottop.core.repository.doll.DollRepository;
import com.hottop.core.repository.merchant.MerchantTradeRepository;
import com.hottop.core.request.argument.base.BaseParamArgumentResolver;
import com.hottop.core.request.argument.filter.EFilterOperator;
import com.hottop.core.request.argument.filter.EFilterWidgetType;
import com.hottop.core.request.argument.filter.FilterArgumentResolver;
import com.hottop.core.request.argument.filter.api.*;
import com.hottop.core.request.argument.flag.FlagArgumentResolver;
import com.hottop.core.request.argument.view.DataView;
import com.hottop.core.request.argument.view.DataViewRegistration;
import com.hottop.core.trading.TradeFactory;
import com.hottop.core.utils.FieldIndicatorTypesUtil;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.RsaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.spring.web.json.Json;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;


@Configuration
public class BaseConfiguration implements ApplicationContextAware, WebMvcConfigurer {
    private static Gson generalGson;
    private static Gson simpleGson;

    private static ApplicationContext context;

    public static String baseHost() {
        switch (getActiveProfile()) {
            case "dev":
                return String.format("%s%s", "http://localhost", StringUtils.isEmpty(getProperty("server.port")) ? "" : ":" + getProperty("server.port"));
        }
        return String.format("http://%s.%s", getActiveProfile(), getProperty("base.host"));
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new FlagArgumentResolver());
        argumentResolvers.add(new FilterArgumentResolver());
        argumentResolvers.add(new BaseParamArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new VersionInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(new BaseParamsInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.addBasenames("classpath:i18n/base");
        messageSource.addBasenames("classpath:i18n/backstage");
        return messageSource;
    }

    @Bean
    public MutablePropertySources propertySources() throws Exception {
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addLast(new ResourcePropertySource("business.properties"));
        propertySources.addLast(new ResourcePropertySource("trade.properties"));
        return propertySources;
    }

    @Bean
    public PropertyResolver propertyResolver(MutablePropertySources propertySources) {
        return new PropertySourcesPropertyResolver(propertySources);
    }

    @Bean
    public RsaUtil rsaUtil() {
        return new RsaUtil();
    }

    public static String getProperty(String code) {
        PropertyResolver propertyResolver = (PropertyResolver) getBean("propertyResolver");
        if (propertyResolver != null) {
            return propertyResolver.getProperty(code);
        }
        return "";
    }

    public static String getMessage(String code, Object... objs) {
        String codeString;
        try {
            codeString = ((MessageSource) getBean("messageSource")).getMessage(code, objs, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            codeString = String.format("cannot find corresponding i18n message: %s", code);
        }
        return codeString;
    }

    @Bean
    public GsonBuilder gsonBuilder() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapter(Json.class, new SpringfoxJsonToGsonAdapter())
                .registerTypeAdapter(Media.class, new MediaSerializer())
                .registerTypeAdapter(ModuleBase.class, new ModuleSerializer()) //注册模块moduleBase序列化器
                .registerTypeAdapter(StatusStatusTracker.class, new StatusStatusSerializer())
                .registerTypeAdapter(TypeIndicator.class, new TypeIndicatorSerializer())
                .registerTypeAdapter(ComponentBase.class, new ComponentSerializer())
                .registerTypeAdapter(ComponentDecorator.class, new ComponentDecoratorSerializer())
                .registerTypeAdapter(ActionBase.class, new ActionSerializer())
                .registerTypeAdapterFactory(new EnumAdapterFactory())
                ;
    }

    @Transactional
    void initDollFacilities(ProgramInterfaceRegistration programInterfaceIndicatorRegistration) throws Exception {
        DollRepository dollRepository = getBean(DollRepository.class);
        //dollRepository.deleteAllInBatch();
        HashMap<Integer, String> checkBoxMap = new HashMap<>();
        checkBoxMap.put(1, "[\"chk_val1\"]");
        checkBoxMap.put(2, "[\"chk_val1\",\"chk_val2\"]");
        checkBoxMap.put(3, "[\"chk_val1\",\"chk_val2\",\"chk_val3\"]");
        checkBoxMap.put(4, "[\"chk_val1\",\"chk_val2\",\"chk_val3\",\"chk_val4\"]");
        HashMap<Integer, String> selectMap = new HashMap<>();
        selectMap.put(1, "[\"sel_val1\"]");
        selectMap.put(2, "[\"sel_val1\",\"sel_val2\"]");
        selectMap.put(3, "[\"sel_val1\",\"sel_val2\",\"sel_val3\"]");
        selectMap.put(4, "[\"sel_val1\",\"sel_val2\",\"sel_val3\",\"sel_val4\"]");
        if (dollRepository.count() <= 0) {
            EntityManagerFactory entityManagerFactory = getBean(EntityManagerFactory.class);
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            for (int i=0; i<1000; i++) {
                String dateDay = String.valueOf(i%30+1);
                if (dateDay.length() == 1) {
                    dateDay = "0"+dateDay;
                }
                String chkbox = checkBoxMap.get(i % 4 + 1);
                String selbox = selectMap.get(i % 4 + 1);
                String sql = "insert into gb_doll (`id`, `create_time`, `update_time`, `delete_time`, `input_string`, `input_long`, `cascade_tree_long`, `select_values`, `select_api_string`, `radio_string`, `checkbox_string`, `duration_long`, `date_date`, `duration_date_date`, `status` ) " +
                        "values (null, '2019-04-"+dateDay+" 00:00:00', '2019-04-"+dateDay+" 00:00:00', null, 'str"+i+"', "+i+", "+i+", '"+selbox+"', 's_a_v"+i+"', 'r_s"+i+"', '"+chkbox+"', 1000, '2019-04-"+dateDay+" 00:00:00', '2019-04-"+dateDay+" 00:00:00', 'status"+i/50+"');";
                Query q = entityManager.createNativeQuery(sql);
                entityManager.getTransaction().begin();
                q.executeUpdate();
                entityManager.getTransaction().commit();
            }
        }

        programInterfaceIndicatorRegistration
                .clazz(Doll.class)
                    .registerNamespace(BaseConstant.Core.NAMESPACE_BACKSTAGE, ProgramInterfaceIndicator.ProgramInterfaceIndicatorBuilder.init(Doll.class)
                            .fieldIndicators(FieldIndicator.FieldIndicatorBuilder.field(Doll_.INPUT_STRING,
                                            getMessage("cms.doll.inputString"),
                                            FilterFieldWidget.simpleInput(getMessage("cms.doll.inputString.hint"))).create(),
                                    FieldIndicator.FieldIndicatorBuilder.field(Doll_.INPUT_LONG,
                                            getMessage("cms.doll.inputLong"),
                                            FilterFieldWidget.simpleInput(getMessage("cms.doll.inputLong.hint"))).explanation(getMessage("cms.doll.inputLong.explanation")).create(),
                                    FieldIndicator.FieldIndicatorBuilder.field(Doll_.CASCADE_TREE_LONG,
                                            getMessage("cms.doll.cascadeTreeLong"),
                                            FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.cascadeTree)
                                                    .hint(getMessage("cms.doll.cascadeTreeLong.hint"))
                                                    .uri("/category/tree", RequestMethod.GET)
                                                    .create()).explanation(getMessage("cms.doll.cascadeTreeLong.explanation")).create(),
                                    FieldIndicator.FieldIndicatorBuilder.field(Doll_.SELECT_VALUES,
                                            getMessage("cms.doll.selectValues"),
                                            FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select)
                                                    .values("value1", "value2", "value3", "value4")
                                                    .create()).explanation(getMessage("cms.doll.selectValues.explanation")).create(),
                                    FieldIndicator.FieldIndicatorBuilder.field(Doll_.SELECT_API_STRING,
                                            getMessage("cms.doll.selectApiString"),
                                            FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select)
                                                    .uri("/doll/selectValues", RequestMethod.GET)
                                                    .create()).create(),
                                    FieldIndicator.FieldIndicatorBuilder.field(Doll_.RADIO_STRING,
                                            getMessage("cms.doll.radioString"),
                                            FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.radio)
                                                    .values("value1", "value2", "value3", "value4")
                                                    .create()).create(),
                                    FieldIndicator.FieldIndicatorBuilder.field(Doll_.CHECKBOX_STRING,
                                            getMessage("cms.doll.checkboxString"),
                                            FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.checkbox)
                                                    .uri("/doll/checkboxValues", RequestMethod.GET)
                                                    .uriParam("key1", "value1")
                                                    .uriParam("key2", "value2")
                                                    .create()).explanation(getMessage("cms.doll.checkboxString.explanation")).create(),
                                    /*FieldIndicator.FieldIndicatorBuilder.field(Doll_.DURATION_LONG,
                                            getMessage("cms.doll.durationLong"),
                                            FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.duration)
                                                    .create()).create(),*/
                                    FieldIndicator.FieldIndicatorBuilder.field(Doll_.DATE_DATE,
                                            getMessage("cms.doll.dateDate"),
                                            FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.date)
                                                    .create()).create(),
                                    /*FieldIndicator.FieldIndicatorBuilder.field(Doll_.DURATION_DATE_DATE,
                                            getMessage("cms.doll.durationDateDate"),
                                            FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.durationDate)
                                                    .create()).create(),*/
                                    FieldIndicator.FieldIndicatorBuilder.field("status",
                                            getMessage("cms.doll.status"),
                                            FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.checkbox)
                                                    .uri("/doll/checkboxValues", RequestMethod.GET)
                                                    .uriParam("key1", "value1")
                                                    .uriParam("key2", "value2")
                                                    .create()).create())
                            .requiredFields(Doll_.INPUT_STRING, Doll_.CASCADE_TREE_LONG)
                            .sortableFields(Doll_.INPUT_STRING, Doll_.ID, Doll_.INPUT_LONG, Doll_.SELECT_VALUES, Doll_.SELECT_API_STRING, "status")
                            .filterFieldIndicators(FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(Doll_.INPUT_STRING, getMessage("cms.doll.inputString"), EFilterOperator.equal)
                                            .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.input).create()).create(),
                                    FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(Doll_.INPUT_LONG,
                                            getMessage("cms.doll.inputLong"),
                                            EFilterOperator.equal, EFilterOperator.greaterThan, EFilterOperator.greaterEqualThan, EFilterOperator.lessThan, EFilterOperator.lessEqualThan)
                                            .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.input).create()).create(),
                                    FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(Doll_.CASCADE_TREE_LONG,
                                            getMessage("cms.doll.cascadeTreeLong"),
                                            EFilterOperator.equal, EFilterOperator.greaterThan, EFilterOperator.greaterEqualThan, EFilterOperator.lessThan, EFilterOperator.lessEqualThan)
                                            .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.cascadeTree)
                                                    .uri("/category/tree", RequestMethod.GET).create()
                                            ).create(),
                                    FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(Doll_.SELECT_VALUES,
                                            getMessage("cms.doll.selectValues"),
                                            EFilterOperator.equal, EFilterOperator.in)
                                            .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select).create()).create(),
                                    FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(Doll_.SELECT_API_STRING,
                                            getMessage("cms.doll.selectApiString"),
                                            EFilterOperator.equal, EFilterOperator.in)
                                            .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select)
                                                    .uri("/doll/selectValues", RequestMethod.GET)
                                                    .create()
                                            ).create(),
                                    FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(Doll_.RADIO_STRING,
                                            getMessage("cms.doll.radioString"),
                                            EFilterOperator.equal, EFilterOperator.in)
                                            .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.radio).values("val1", "val2", "val3").create()).create(),
                                    FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(Doll_.CHECKBOX_STRING,
                                            getMessage("cms.doll.checkboxString"),
                                            EFilterOperator.equal, EFilterOperator.in)
                                            .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.checkbox)
                                                    .uri("/doll/checkboxValues", RequestMethod.GET)
                                                    .uriParam("key1", "value1")
                                                    .uriParam("key2", "value2")
                                                    .create()
                                            ).create(),
                                    FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(Doll_.DURATION_LONG,
                                            getMessage("cms.doll.durationLong"),
                                            EFilterOperator.equal, EFilterOperator.greaterThan, EFilterOperator.greaterEqualThan, EFilterOperator.lessThan, EFilterOperator.lessEqualThan)
                                            .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.duration).create()).create(),
                                    FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(Doll_.DATE_DATE,
                                            getMessage("cms.doll.dateDate"),
                                            EFilterOperator.equal)
                                            .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.durationDate).create()).create(),
                                    FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(Doll_.DURATION_DATE_DATE,
                                            getMessage("cms.doll.durationDateDate"),
                                            EFilterOperator.equal)
                                            .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.durationDate).create()).create(),
                                    FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField("status",
                                            getMessage("cms.doll.status"),
                                            EFilterOperator.equal, EFilterOperator.in)
                                            .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select)
                                                    .uri("/doll/statusValues", RequestMethod.GET)
                                                    .uriParam("key1", "value1")
                                                    .create()
                                            ).create())
                            .create());

//        TypeCabinet dollCabinet =  TypeFactory.registerMap(Doll.class, "d");
//        TypeIndicator defaultType = TypeFactory.newType(Doll.class, "default")
//                .registerMeta(TypeMeta.build("a", EMetaType.string),
//                        TypeMeta.build("b", EMetaType.string),
//                        TypeMeta.build("c", EMetaType.list),
//                        TypeMeta.build("d", EMetaType.map).required());
//        dollCabinet.register(defaultType);
    }

    private void initTypeFactory() throws Exception {
    }

    private static void initStatusFactory() throws Exception {
        IStatusFlow<Class<?>, Status> merchantStatusFlow = StatusFactory.registerClazz(MerchantTrade.class, "mt");
        Status mtPayPrepay = StatusFactory.newStatus(MerchantTrade.class, "pay", "prepay");
        Status mtPayPayed = StatusFactory.newStatus(MerchantTrade.class, "pay", "payed");
        Status mtPayFailure = StatusFactory.newStatus(MerchantTrade.class, "pay", "failure");
        Status mtPayCancel = StatusFactory.newStatus(MerchantTrade.class, "pay", "cancel");
        merchantStatusFlow.registerStatuses(mtPayPrepay, mtPayPayed, mtPayFailure, mtPayCancel);
        merchantStatusFlow.getTrackerByStatus(mtPayPrepay.status())
                .registerEvent(EStatusEvent.success, mtPayPayed)
                .registerEvent(EStatusEvent.cancel, mtPayCancel)
                .registerEvent(EStatusEvent.failure, mtPayFailure);

        //采购单状态
        CommercePurchaseOrder.initStatus();
    }

    @Bean
    public DataViewRegistration dataViewRegistration() throws Exception {
        DataViewRegistration dataViewRegistration = new DataViewRegistration();
        dataViewRegistration
                .clazz(CommerceBrand.class)
                    .addView("list", DataView.create(CommerceBrand_.name, CommerceBrand_.country, CommerceBrand_.description))
                .and().clazz(CommerceSpu.class);
        return dataViewRegistration;
    }

    @Bean
    public ProgramInterfaceRegistration programInterfaceIndicatorRegistration() throws Exception {
        ProgramInterfaceRegistration programInterfaceRegistration = new ProgramInterfaceRegistration();
        programInterfaceRegistration.registerNamespaces(BaseConstant.Core.NAMESPACE_BACKSTAGE);
        initDollFacilities(programInterfaceRegistration);
        programInterfaceRegistration
                .clazz(CommerceBrand.class)
                    .registerNamespace(BaseConstant.Core.NAMESPACE_BACKSTAGE, ProgramInterfaceIndicator.ProgramInterfaceIndicatorBuilder.init(CommerceBrand.class)
                            .fieldIndicators(
                                    FieldIndicator.FieldIndicatorBuilder.field(CommerceBrand_.NAME,
                                            getMessage("commerce.brand.name"),
                                            FilterFieldWidget.simpleInput()).create(),
                                    FieldIndicator.FieldIndicatorBuilder.field(CommerceBrand_.COUNTRY,
                                            getMessage("commerce.brand.country"),
                                            FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select)
                                                    .uri("/brand/country", RequestMethod.GET)
                                                    .create()).explanation(getMessage("commerce.brand.country.explanation")).create(),
                                    FieldIndicator.FieldIndicatorBuilder.field(
                                            CommerceBrand_.CATEGORY_ID,
                                            getMessage("commerce.brand.categoryId"),
                                            FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.cascadeTree)
                                                    .uri("/category/tree", RequestMethod.GET).create()
                                    ).explanation(getMessage("commerce.brand.categoryId.explanation")).create(),
                                    FieldIndicator.FieldIndicatorBuilder.field(CommerceBrand_.DESCRIPTION,
                                            getMessage("commerce.brand.description"),
                                            FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.input).create())
                                            .create(),
                                    FieldIndicator.FieldIndicatorBuilder.field(
                                            CommerceBrand_.AVATAR,
                                            getMessage("commerce.brand.avatar"),
                                            FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.image).create()
                                    ).create()
                            )
                            .requiredFields(CommerceBrand_.NAME, CommerceBrand_.CATEGORY_ID, CommerceBrand_.COUNTRY, CommerceBrand_.DESCRIPTION)
                            .sortableFields(CommerceBrand_.NAME, CommerceBrand_.COUNTRY)
                            .filterFieldIndicators(
                                    FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(CommerceBrand_.NAME,
                                            getMessage("commerce.brand.name"),
                                            EFilterOperator.equal)
                                            .widget(FilterFieldWidget.simpleInput()).create(),
                                    FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(CommerceBrand_.COUNTRY,
                                            getMessage("commerce.brand.country"),
                                            EFilterOperator.equal, EFilterOperator.in)
                                            .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select)
                                                    .uri("/brand/country", RequestMethod.GET)
                                                    .create())
                                            .create())
                            .create())
                .and().clazz(CommerceCategory.class)
                .registerNamespace(BaseConstant.Core.NAMESPACE_BACKSTAGE, ProgramInterfaceIndicator.ProgramInterfaceIndicatorBuilder.init(CommerceCategory.class)
                        .fieldIndicators(
                                FieldIndicator.FieldIndicatorBuilder.field(CommerceCategory_.NAME,
                                        getMessage("commerce.category.name"),
                                        FilterFieldWidget.simpleInput()
                                        ).explanation(getMessage("commerce.category.name.explanation")
                                ).create(),
                                FieldIndicator.FieldIndicatorBuilder.field(CommerceCategory_.SPECIFICATIONS,
                                        getMessage("commerce.category.specification"),
                                        FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.extra).extras("categorySpecification", 1).create())
                                        .render(FieldIndicatorTypesUtil.SPECIFICATION)
                                        .create(),
                                FieldIndicator.FieldIndicatorBuilder.field(CommerceCategory_.ATTRIBUTES,
                                        getMessage("commerce.category.attributes"),
                                        FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.extra).extras("categoryAttribute", 1).create())
                                        .render(FieldIndicatorTypesUtil.StrArray)
                                        .create()
                        )
                        .requiredFields(CommerceCategory_.NAME, CommerceCategory_.SPECIFICATIONS, CommerceCategory_.ATTRIBUTES)
                        .filterFieldIndicators(FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(CommerceCategory_.NAME,
                                getMessage("commerce.category.name"),
                                EFilterOperator.like)
                                .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.extra).extras("category_extra", "null").create()).create())
                        .create())
                .and().clazz(CommerceAttribute.class)
                .registerNamespace(BaseConstant.Core.NAMESPACE_BACKSTAGE, ProgramInterfaceIndicator.ProgramInterfaceIndicatorBuilder.init(CommerceAttribute.class)
                        .fieldIndicators(
                                FieldIndicator.FieldIndicatorBuilder.field(CommerceAttribute_.NAME,
                                        getMessage("commerce.attribute.name"),
                                        FilterFieldWidget.simpleInput()).create(),
                                FieldIndicator.FieldIndicatorBuilder.field(CommerceAttribute_.RECOMMENDATION_VALUES,
                                        getMessage("commerce.attribute.recommendationValues"),
                                        FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.extra).create())
                                        .render(FieldIndicatorTypesUtil.StrArray)
                                        .create(),
                                FieldIndicator.FieldIndicatorBuilder.field(CommerceAttribute_.TYPE,
                                        getMessage("commerce.attribute.type"),
                                        FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select)
                                                .uri("/attribute/type", RequestMethod.GET)
                                                .create()).create())
                        .requiredFields(CommerceAttribute_.NAME, CommerceAttribute_.TYPE)
                        .sortableFields(CommerceAttribute_.NAME)
                        .filterFieldIndicators(
                                FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(CommerceAttribute_.NAME,
                                        getMessage("commerce.attribute.name"),
                                        EFilterOperator.like)
                                        .widget(FilterFieldWidget.simpleInput())
                                        .create(),
                                FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(CommerceAttribute_.TYPE,
                                        getMessage("commerce.attribute.type"),
                                        EFilterOperator.equal)
                                        .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select)
                                                .uri("/attribute/type", RequestMethod.GET)
                                                .create())
                                        .create()
                        ).create()
                )
                .and().apply(CommerceService.class, BaseConstant.Core.NAMESPACE_BACKSTAGE, CommerceService.getProgramInterfaceIndicator())
                .and().apply(CommerceSpu.class, BaseConstant.Core.NAMESPACE_BACKSTAGE, CommerceSpu.getProgramInterfaceIndicator())
                .and().apply(CommerceSpecification.class, BaseConstant.Core.NAMESPACE_BACKSTAGE, CommerceSpecification.getProgramInterfaceIndicator())
                .and().apply(Capability.class, BaseConstant.Core.NAMESPACE_BACKSTAGE, Capability.getProgramInterfaceIndicator())
                .and().apply(CommercePurchaseOrder.class, BaseConstant.Core.NAMESPACE_BACKSTAGE, CommercePurchaseOrder.getProgramInterfaceIndicator())//采购单
        ;
        return programInterfaceRegistration;
    }

    @Bean
    public TradeFactory tradeFactory(ResourceLoader resourceLoader, MerchantTradeRepository merchantTradeRepository) {
        return new TradeFactory(resourceLoader, merchantTradeRepository);
    }

    @Bean
    @Autowired
    public ExtractorFactory extractorFactory(List<ICmsExtractor> extractors) {
        ExtractorFactory extractorFactory = new ExtractorFactory();
        extractorFactory.registerExtractors(extractors);
        return extractorFactory;
    }

    public static String getActiveProfile() {
        if (context != null) {
            String[] profiles = context.getEnvironment().getActiveProfiles();
            return profiles[profiles.length-1];
        }
        return "";
    }

    public static String currentProfileHost() {
        return String.format("%s.hottop.live", getActiveProfile());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        try {
            initStatusFactory();
            initTypeFactory();
        } catch (Exception ex) {
            LogUtil.error(String.format("com.hottop.application init initStatusFactory error: %s", ex.getMessage()));
        }
    }

    public static Object getBean(String beanName) {
        if (context != null) {
            return context.getBean(beanName);
        }
        return null;
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static Gson generalGson() {
        if (generalGson == null) {
            generalGson = ((GsonBuilder)BaseConfiguration.getBean("gsonBuilder")).create();
        }
        return generalGson;
    }


    public static Gson simpleGson() {
        if (simpleGson == null) {
            simpleGson = new Gson();
        }
        return simpleGson;
    }

    /**
     * 添加Spring 的加密类
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
