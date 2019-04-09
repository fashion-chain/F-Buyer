package com.hottop.core.request.argument.flag;

import com.hottop.core.BaseConstant;
import com.hottop.core.request.argument.annotation.Flag;
import com.hottop.core.request.argument.sort.SortStringResolver;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class FlagArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(Flag.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        String flag = nativeWebRequest.getParameter(BaseConstant.Request.Argument.FLAG);
        String sort = nativeWebRequest.getParameter(BaseConstant.Request.Argument.SORT);
        if (StringUtils.isEmpty(flag)) {
            flag = new FlagPageSizeResolver(0, BaseConstant.Response.PAGE_SIZE).toFlag();
        }
        FlagPageable flagPageable = new FlagPageSizeRequest(flag);
        flagPageable.setSortResolver(new SortStringResolver(sort));
        return flagPageable;
    }
}
