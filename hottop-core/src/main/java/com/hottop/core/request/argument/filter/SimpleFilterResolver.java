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
    private ArrayList<Specification<T>> andSpecifications;
    private Specification<T> orSpecification;

    private String filterString;
    private HashMap<EFilterFunction, HashMap<String, String>> funcParametersMapper;
    private DataViewRegistration dataViewRegistration;

    public SimpleFilterResolver(String filterString, HashMap<EFilterFunction, HashMap<String, String>> funcParametersMapper, DataViewRegistration dataViewRegistration) {
        this.filterString = filterString == null ? "" : filterString;
        this.funcParametersMapper = funcParametersMapper;
        this.dataViewRegistration = dataViewRegistration;
    }

    @Override
    public ArrayList<Specification<T>> andSpecifications() {
        if (this.andSpecifications == null) {
            this.andSpecifications = new ArrayList<>();
            for (String parameterOrSpecifications: filterString.split(BaseConstant.Common.FILTER_SPECIFICATION_OR_SPLITTER)) {
                ArrayList<Specification<T>> andSpecifications = new ArrayList<>();
                for (String parameterAndSpecification: parameterOrSpecifications.split(BaseConstant.Common.FILTER_SPECIFICATION_AND_SPLITTER)) {
                    if (StringUtils.isEmpty(parameterAndSpecification)) {
                        continue;
                    }
                    andSpecifications.add(new FilterParameterSpecification<>(parameterAndSpecification));
                }
                this.andSpecifications.add(FilterParameterSpecification.unionAnd(andSpecifications));
            }
        }
        return this.andSpecifications;
    }

    @Override
    public Specification<T> orSpecification() {
        if (this.orSpecification == null) {
            this.orSpecification = FilterParameterSpecification.unionOr(andSpecifications());
        }
        return this.orSpecification;
    }

    @Override
    public IFilterFunctionResolver functionResolver(Class<T> clazz) {
        if (filterFunctionResolver == null) {
            filterFunctionResolver = new SimpleFilterFunctionResolver(clazz, funcParametersMapper, dataViewRegistration);
        }
        return this.filterFunctionResolver;
    }
}
