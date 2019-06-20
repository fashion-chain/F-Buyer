package com.hottop.core.controller;

import com.hottop.core.model.user.ProvinceCity;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.service.provinceCity.ProvinceCityService;
import com.hottop.core.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 省份 城区控制器
 */
@RestController
@RequestMapping(path = "/provinceCity")
public class ProvinceCityController extends  EntityBaseController<ProvinceCity>{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProvinceCityService provinceCityService;

    @Override
    public Class<ProvinceCity> clazz() {
        return ProvinceCity.class;
    }

    @Override
    public EntityBaseService service() {
        return provinceCityService;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public Response list() {
        return provinceCityService.list();
    }
}
