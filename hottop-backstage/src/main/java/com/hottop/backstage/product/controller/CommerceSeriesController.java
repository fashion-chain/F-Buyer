package com.hottop.backstage.product.controller;

import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.core.model.commerce.CommerceSeries;
import com.hottop.core.product.service.CommerceSeriesService;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//系列
@RestController
@RequestMapping("/series")
public class CommerceSeriesController extends BackstageBaseController<CommerceSeries> {


    @Autowired
    private CommerceSeriesService commerceSeriesService;

    @Override
    public Class<CommerceSeries> clazz() {
        return CommerceSeries.class;
    }

    @Override
    public EntityBaseService service() {
        return commerceSeriesService;
    }

    //新增
    @PostMapping("/")
    @Override
    public Response create(@RequestBody CommerceSeries entity) throws Exception {
        return super.create(entity);
    }
}
