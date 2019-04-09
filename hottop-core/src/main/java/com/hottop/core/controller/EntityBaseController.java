package com.hottop.core.controller;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.request.argument.annotation.Filter;
import com.hottop.core.request.argument.filter.IFilterFunctionResolver;
import com.hottop.core.request.argument.filter.IFilterResolver;
import com.hottop.core.request.argument.view.DataViewRegistration;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Validated
public abstract class EntityBaseController<T extends EntityBase> {

    public abstract Class<T> clazz();

    public abstract EntityBaseService service();

    public Response create(@Valid T entity) throws Exception {
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(service().save(entity))
                .create();
    }


    public Response views() throws Exception {
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(((DataViewRegistration) BaseConfiguration.getBean("dataViewRegistration"))
                        .clazz(clazz()).views())
                .create();
    }

    public Response getFields() throws Exception {
        ArrayList<Field> fields = service().fields(clazz());
        ArrayList<String> fieldNames = new ArrayList<>();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(fieldNames)
                .create();
    }

    public Response filter(IFilterResolver<T> filterResolver) throws Exception {
        Page<T> content = service().filter(clazz(), filterResolver);
        IFilterFunctionResolver functionResolver = filterResolver.functionResolver(clazz());
        Response.ResponseBuilder builder = Response.ResponseBuilder.result(EResponseResult.OK)
                .pageableSortable(content, functionResolver.flagPageable());
        if (!functionResolver.fieldExporter().valid() && !functionResolver.viewExporter().valid()) {
            return builder
                    .data(content.getContent())
                    .create();
        } else {
            List<HashMap<String, Object>> exporterList = new ArrayList<>();
            if (functionResolver.fieldExporter().valid()) {
                for (T entity: content.getContent()) {
                    exporterList.add(functionResolver.fieldExporter().encapsulateExporter(entity));
                }
            } else if (functionResolver.viewExporter().valid()) {
                for (T entity: content.getContent()) {
                    exporterList.add(functionResolver.viewExporter().encapsulateExporter(entity));
                }
            }
            return builder
                    .data(exporterList)
                    .create();
        }
    }
}
