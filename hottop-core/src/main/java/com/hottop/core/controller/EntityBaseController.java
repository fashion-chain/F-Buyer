package com.hottop.core.controller;

import com.hottop.core.BaseConstant;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.request.argument.annotation.ProgramInterfaceNamespace;
import com.hottop.core.request.argument.filter.IFilterFunctionResolver;
import com.hottop.core.request.argument.filter.IFilterResolver;
import com.hottop.core.request.argument.filter.api.ProgramInterfaceIndicator;
import com.hottop.core.request.argument.filter.api.ProgramInterfaceRegistration;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.CommonUtil;
import com.hottop.core.utils.LogUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@Validated
public abstract class EntityBaseController<T extends EntityBase> {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(clazz());

    public abstract Class<T> clazz();

    public abstract EntityBaseService service();

    public Response create(@Valid T entity) throws Exception {
        try {
            return Response.ResponseBuilder.result(EResponseResult.COMMON_SUCCESS_ADD)
                    .data(service().save(entity))
                    .create();
        } catch (Exception e) {
            LogUtil.error(String.format("create error: %s", e.getMessage()), e.getStackTrace());
            return Response.ResponseBuilder.result(EResponseResult.COMMON_ERROR_ADD)
                    .simpleMessage(e.getMessage())
                    .error(CommonUtil.printStackTraceElements(e.getStackTrace()))
                    .create();
        }
    }

    public Response getOne(Long id) throws Exception {
        try {
            T one = (T) service().findOne(clazz(), id);
            if (one != null) {
                return Response.ResponseBuilder.result(EResponseResult.COMMON_SUCCESS_GetDetail)
                        .data(one)
                        .create();
            }
        } catch (Exception e) {
            LogUtil.error(e.getStackTrace());
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                    .simpleMessage(e.getMessage())
                    .error(CommonUtil.printStackTraceElements(e.getStackTrace()))
                    .create();
        }
        return Response.ResponseBuilder.result(EResponseResult.COMMON_ERROR_GetDetail)
                .create();
    }

    public Response delete( Long id) throws Exception {
        try {
            service().delete(clazz(), id);
            return Response.ResponseBuilder.result(EResponseResult.COMMON_SUCCESS_DELETE)
                    .create();
        } catch (Exception e) {
            LogUtil.error(e.getStackTrace());
            return Response.ResponseBuilder.result(EResponseResult.COMMON_ERROR_DELETE)
                    .simpleMessage(e.getMessage())
                    .error(CommonUtil.printStackTraceElements(e.getStackTrace()))
                    .create();
        }
    }

    public Response programInterface() throws Exception {
        ProgramInterfaceNamespace programInterfaceNamespace = clazz().getAnnotation(ProgramInterfaceNamespace.class);
        String namespace = BaseConstant.Core.NAMESPACE_BACKSTAGE;
        if (programInterfaceNamespace != null) {
            namespace = programInterfaceNamespace.namespace();
        }
        ProgramInterfaceIndicator programInterfaceIndicator = BaseConfiguration.getBean(ProgramInterfaceRegistration.class).getInterfaceIndicator(clazz(), namespace);
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(programInterfaceIndicator)
                .create();
    }

    public Response filter(IFilterResolver<T> filterResolver) throws Exception {
        //todo verify is all the operates available for the class.
        Page<T> content = service().filter(clazz(), filterResolver);
        IFilterFunctionResolver functionResolver = filterResolver.functionResolver(clazz());
        Response.ResponseBuilder builder = Response.ResponseBuilder.result(EResponseResult.OK)
                .pageableSortableWithTotalPages(content, functionResolver.flagPageable());
        if (!functionResolver.fieldExporter().valid() && !functionResolver.viewExporter().valid()) {
            return builder
                    .data(content.getContent())
                    .create();
        } else {
            List<HashMap<String, Object>> exporterList = new ArrayList<>();
            if (functionResolver.fieldExporter().valid()) {
                for (T entity : content.getContent()) {
                    exporterList.add(functionResolver.fieldExporter().encapsulateExporter(entity));
                }
            } else if (functionResolver.viewExporter().valid()) {
                for (T entity : content.getContent()) {
                    exporterList.add(functionResolver.viewExporter().encapsulateExporter(entity));
                }
            }
            return builder
                    .data(exporterList)
                    .create();
        }
    }
}
