package com.hottop.core.response.advicer;

import com.hottop.core.BaseConstant;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.cms.CmsUtil;
import com.hottop.core.model.cms.annotation.ComponentConverter;
import com.hottop.core.model.cms.bean.TemplateComponent;
import com.hottop.core.model.cms.bean.action.EActionType;
import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.EComponentType;
import com.hottop.core.model.cms.bean.extractor.ExtractorFactory;
import com.hottop.core.model.cms.bean.exception.ActionTypeNotSupportException;
import com.hottop.core.model.cms.bean.exception.ExtractorNotFoundException;
import com.hottop.core.model.cms.bean.exception.ComponentTypeNotSupportException;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class ComponentAdvice implements ResponseBodyAdvice<Response> {


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        if (methodParameter.hasMethodAnnotation(RequestMapping.class)) {
            RequestMapping requestMapping = methodParameter.getMethodAnnotation(RequestMapping.class);
            for (RequestMethod method : requestMapping.method()) {
                if (method == RequestMethod.GET) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Response beforeBodyWrite(Response response,
                                    MethodParameter methodParameter,
                                    MediaType mediaType,
                                    Class<? extends HttpMessageConverter<?>> aClass,
                                    ServerHttpRequest serverHttpRequest,
                                    ServerHttpResponse serverHttpResponse) {
        HttpServletRequest request = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        EComponentType componentType = null;
        EActionType actionType = null;
        if (request.getParameterMap().containsKey(BaseConstant.Request.Argument.COMPONENT_TYPE)) {
            try {
                componentType = EComponentType.valueOf(request.getParameter(BaseConstant.Request.Argument.COMPONENT_TYPE));
                if (request.getParameterMap().containsKey(BaseConstant.Request.Argument.ACTION_TYPE)) {
                    actionType = EActionType.valueOf(request.getParameter(BaseConstant.Request.Argument.ACTION_TYPE));
                }
            } catch (IllegalArgumentException ex) {
                return Response.ResponseBuilder.result(EResponseResult.ERROR_CMS_EXTRACT)
                        .error(ex.getMessage())
                        .create();
            }
        }
        if (methodParameter.getMethod().getAnnotation(ComponentConverter.class) != null) {
            ComponentConverter componentConverter = methodParameter.getMethod().getAnnotation(ComponentConverter.class);
            if (componentConverter.component().length > 0) {
                componentType = componentConverter.component()[0];
            }
            if (componentConverter.action().length > 0) {
                actionType = componentConverter.action()[0];
            }
        }
        if (methodParameter.getMethod().getAnnotation(ComponentConverter.class) != null || request.getParameterMap().containsKey(BaseConstant.Request.Argument.COMPONENT_TYPE)) {
            if (response.getData() != null) {
                ExtractorFactory extractorFactory = BaseConfiguration.getBean(ExtractorFactory.class);
                try {
                    if (response.getData() instanceof List) {
                        List dataList = (List) response.getData();
                        if (dataList.size() > 0) {
                            List<ComponentBase> components = new ArrayList<>();
                            for (Object obj : (List) response.getData()) {
                                components.add(extractorFactory.encapsulateComponent(obj, componentType, actionType));
                            }
                            response.setData(components);
                        }
                    } else {
                        response.setData(extractorFactory.encapsulateComponent(response.getData(), componentType, actionType));
                    }
                } catch (ExtractorNotFoundException ex1) {
                    return Response.ResponseBuilder.result(EResponseResult.ERROR_CMS_EXTRACTOR_NOT_FOUND)
                            .error(ex1.getMessage())
                            .create();
                } catch (ComponentTypeNotSupportException | ActionTypeNotSupportException ex2) {
                    return Response.ResponseBuilder.result(EResponseResult.ERROR_CMS_COMPONENT_TYPE_NOT_SUPPORT)
                            .error(ex2.getMessage())
                            .create();
                }
            }
            return response;
        }
        return response;
    }
}
