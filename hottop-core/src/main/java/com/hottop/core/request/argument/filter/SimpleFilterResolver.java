package com.hottop.core.request.argument.filter;

import com.hottop.core.BaseConstant;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.request.argument.view.DataViewRegistration;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.HashMap;

public class SimpleFilterResolver<T extends EntityBase> implements IFilterResolver<T> {
    private SimpleFilterFunctionResolver filterFunctionResolver;
    private ArrayList<Specification<T>> orSpecifications;
    private Specification<T> andSpecification;

    private String filterString;
    private HashMap<EFilterFunction, HashMap<String, String>> funcParametersMapper;
    private DataViewRegistration dataViewRegistration;

    public SimpleFilterResolver(String filterString, HashMap<EFilterFunction, HashMap<String, String>> funcParametersMapper, DataViewRegistration dataViewRegistration) {
        this.filterString = filterString == null ? "" : filterString;
        this.funcParametersMapper = funcParametersMapper;
        this.dataViewRegistration = dataViewRegistration;
    }

    @Override
    public ArrayList<Specification<T>> orSpecifications() {
        if (this.orSpecifications == null) {
            this.orSpecifications = new ArrayList<>();
            for (String parameterAndSpecifications: filterString.split(BaseConstant.Common.FILTER_SPECIFICATION_AND_SPLITTER)) {
                ArrayList<Specification<T>> orSpecifications = new ArrayList<>();
                for (String parameterOrSpecification: parameterAndSpecifications.split(BaseConstant.Common.FILTER_SPECIFICATION_OR_SPLITTER)) {
                    if (StringUtils.isEmpty(parameterOrSpecification)) {
                        continue;
                    }
                    orSpecifications.add(new FilterParameterSpecification<>(parameterOrSpecification));
                }
                this.orSpecifications.add(FilterParameterSpecification.unionOr(orSpecifications));
            }
        }
        return this.orSpecifications;
    }

    @Override
    public Specification<T> andSpecification() {
        if (this.andSpecification == null) {
            this.andSpecification = FilterParameterSpecification.unionAnd(orSpecifications());
        }
        return this.andSpecification;
    }

    @Override
    public IFilterFunctionResolver functionResolver(Class<T> clazz) {
        if (filterFunctionResolver == null) {
            filterFunctionResolver = new SimpleFilterFunctionResolver(clazz, funcParametersMapper, dataViewRegistration);
        }
        return this.filterFunctionResolver;
    }
}
