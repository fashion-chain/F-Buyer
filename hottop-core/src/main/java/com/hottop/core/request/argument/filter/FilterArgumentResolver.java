package com.hottop.core.request.argument.filter;

import com.hottop.core.request.argument.annotation.Filter;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;

public class FilterArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(Filter.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        //demo filterString: filter:price_<_200,price_>=_500;province_in_beijing|shanghai;title_like_saint
        //demo fields:id,username

        String filterParam = methodParameter.getParameterAnnotation(Filter.class).parameter();
        if (isFunctionArgument(filterParam)) {
            throw new Exception("@Filter $parameter argument error.");
        }
        String filterString = nativeWebRequest.getParameter(filterParam);
//        Class clazz = methodParameter.getParameterAnnotation(Filter.class).filter();
//        if (!EntityBase.class.isAssignableFrom(clazz)) {
//            throw new Exception(String.format("@Filter $filter argument error. %s not support", clazz.getName()));
//        } else {
        HashMap<EFilterFunction, HashMap<String, String>> funcParametersMapper = new HashMap<>();
        for (EFilterFunction function: EFilterFunction.values()) {
            HashMap<String, String> parameterMapper = new HashMap<>();
            for (String occupiedParameter: function.occupiedParameters()) {
                if (nativeWebRequest.getParameterMap().containsKey(occupiedParameter)) {
                    parameterMapper.put(occupiedParameter, nativeWebRequest.getParameter(occupiedParameter));
                }
            }
            if (parameterMapper.size() > 0) {
                funcParametersMapper.put(function, parameterMapper);
            }
        }
        return FilterResolverFactory.create(filterString, funcParametersMapper);
//        }
    }

    public static boolean isFunctionArgument(String fieldName) {
        boolean isFunctionArgument = false;
        for (EFilterFunction funcName : EFilterFunction.values()) {
            if (StringUtils.equals(funcName.name(), fieldName)) {
                isFunctionArgument = true;
                break;
            }
        }
        return isFunctionArgument;
    }
}
