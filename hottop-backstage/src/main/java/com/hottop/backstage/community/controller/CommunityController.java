package com.hottop.backstage.community.controller;

import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.backstage.community.service.CommunityService;
import com.hottop.core.cms.service.PageService;
import com.hottop.core.model.cms.Page;
import com.hottop.core.model.community.Community;
import com.hottop.core.repository.community.CommunityRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/community")
public class CommunityController extends BackstageBaseController<Community> {

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private PageService pageService;

    @Autowired
    private CommunityService communityService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Response communityTest(@RequestParam(name = "communityId") Long communityId) {
        Optional<Community> optional = communityRepository.findById(communityId);
        Community community = optional.isPresent() ? optional.get() : null;
        if (community != null && community.getPageId() != 0) {
            Page page = pageService.getPageByIdOrName(community.getPageId(), null);
            return Response.ResponseBuilder.result(EResponseResult.OK).data(pageService.decorate(page, community)).create();
        }
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).create();
    }

    @Override
    public Class<Community> clazz() {
        return Community.class;
    }

    @Override
    public EntityBaseService service() {
        return communityService;
    }
}
