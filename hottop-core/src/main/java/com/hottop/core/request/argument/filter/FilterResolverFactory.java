package com.hottop.core.request.argument.filter;

<<<<<<< HEAD
=======
import com.hottop.core.BaseConstant;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.request.argument.view.DataViewRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
<<<<<<< HEAD
=======
import java.util.Map;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

@Component
public class FilterResolverFactory {

<<<<<<< HEAD


=======
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    public static <T extends EntityBase> IFilterResolver<T> create(String filterString,
                                                                   HashMap<EFilterFunction, HashMap<String, String>> funcParametersMapper) {

        return new SimpleFilterResolver<T>(filterString,
                funcParametersMapper,
                (DataViewRegistration) BaseConfiguration.getBean("dataViewRegistration"));
    }
}
