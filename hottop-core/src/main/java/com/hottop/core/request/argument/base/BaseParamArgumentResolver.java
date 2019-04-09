package com.hottop.core.request.argument.base;

import com.hottop.core.BaseConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class BaseParamArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == BaseParams.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        BaseParams baseParams = new BaseParams();
        baseParams.setHeaderParam(new BaseParams.HeaderParam());
        String headerString = nativeWebRequest.getHeader(BaseConstant.Request.Header.HEADER_BASE_AGENT);
        if (StringUtils.isEmpty(headerString)) {
            return baseParams;
        }
        for (String headerName: headerString.split(BaseConstant.Request.Header.SPLITTER)) {
            switch (headerName) {
                case BaseConstant.Request.Header.APP_VERSION:
                    baseParams.getHeaderParam().setAppVersion(nativeWebRequest.getHeader(headerName));
                    break;
                case BaseConstant.Request.Header.SYSTEM_VERSION:
                    baseParams.getHeaderParam().setSystemVersion(nativeWebRequest.getHeader(headerName));
                    break;
                case BaseConstant.Request.Header.DEVICE_MODEL:
                    baseParams.getHeaderParam().setDeviceModel(nativeWebRequest.getHeader(headerName));
                    break;
                case BaseConstant.Request.Header.DEVICE_ID:
                    baseParams.getHeaderParam().setDeviceId(nativeWebRequest.getHeader(headerName));
                    break;
                case BaseConstant.Request.Header.NETWORK_TYPE:
                    baseParams.getHeaderParam().setNetworkType(nativeWebRequest.getHeader(headerName));
                    break;
                case BaseConstant.Request.Header.LATITUDE_LONGITUDE:
                    baseParams.getHeaderParam().setLatitudeLongitude(nativeWebRequest.getHeader(headerName));
                    break;
                case BaseConstant.Request.Header.RESOLUTION_RATIO:
                    baseParams.getHeaderParam().setResolutionRatio(nativeWebRequest.getHeader(headerName));
                    break;
                case BaseConstant.Request.Header.ADAPTER_WIDTH:
                    baseParams.getHeaderParam().setAdapterWidth(nativeWebRequest.getHeader(headerName));
                    break;
                case BaseConstant.Request.Header.USER_ID:
                    baseParams.getHeaderParam().setUserId(nativeWebRequest.getHeader(headerName));
                    break;
            }
        }
        return baseParams;
    }
}
