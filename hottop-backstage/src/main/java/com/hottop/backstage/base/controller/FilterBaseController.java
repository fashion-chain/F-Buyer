package com.hottop.backstage.base.controller;

import com.hottop.core.controller.EntityBaseController;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.request.argument.annotation.Filter;
import com.hottop.core.request.argument.filter.IFilterResolver;
import com.hottop.core.response.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public abstract class FilterBaseController<T extends EntityBase> extends EntityBaseController<T> {

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public Response create(@RequestBody T entity) throws Exception {
        return super.create(entity);
    }

    @RequestMapping(path = "/interface", method = RequestMethod.GET)
    public Response getFields() throws Exception {
        return super.programInterface();
    }

    @RequestMapping(path = "/filter", method = RequestMethod.GET)
    public Response filter(@Filter IFilterResolver<T> filterResolver) throws Exception {
        return super.filter(filterResolver);
    }
}
