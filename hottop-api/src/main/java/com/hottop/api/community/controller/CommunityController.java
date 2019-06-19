package com.hottop.api.community.controller;

import com.hottop.api.base.controller.ApiBaseController;
import com.hottop.api.community.service.CommunityService;
import com.hottop.core.model.community.Community;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/community")
public class CommunityController extends ApiBaseController<Community> {

    @Autowired
    private CommunityService communityService;

    @Override
    public Class<Community> clazz() {
        return Community.class;
    }

    @Override
    public EntityBaseService service() {
        return communityService;
    }
}
