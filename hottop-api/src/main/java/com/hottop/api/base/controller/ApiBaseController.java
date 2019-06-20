package com.hottop.api.base.controller;

import com.hottop.core.controller.EntityBaseController;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.request.argument.annotation.Filter;
import com.hottop.core.request.argument.filter.IFilterResolver;
import com.hottop.core.response.Response;
<<<<<<< HEAD
=======
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public abstract class ApiBaseController<T extends EntityBase> extends EntityBaseController<T> {

<<<<<<< HEAD
    @RequestMapping(path = "/views", method = RequestMethod.GET)
    public Response views() throws Exception {
        return super.views();
    }
    
=======
    public Logger logger = LoggerFactory.getLogger(getClass());

>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    @RequestMapping(path = "/filter", method = RequestMethod.GET)
    public Response filter(@Filter IFilterResolver<T> filterResolver) throws Exception {
        return super.filter(filterResolver);
    }
}
