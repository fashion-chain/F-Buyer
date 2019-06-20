package com.hottop.core.request.argument.filter;

import com.hottop.core.BaseConstant;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.request.argument.view.DataViewRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FilterResolverFactory {

    public static <T extends EntityBase> IFilterResolver<T> create(String filterString,
                                                                   HashMap<EFilterFunction, HashMap<String, String>> funcParametersMapper) {

        return new SimpleFilterResolver<T>(filterString,
                funcParametersMapper,
                (DataViewRegistration) BaseConfiguration.getBean("dataViewRegistration"));
    }
}
