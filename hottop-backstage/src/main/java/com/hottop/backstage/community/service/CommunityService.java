package com.hottop.backstage.community.service;

import com.hottop.core.cms.service.PageService;
import com.hottop.core.model.community.Community;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.community.CommunityRepository;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityService extends EntityBaseService<Community, Long> {

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private PageService pageService;


    @Override
    public EntityBaseRepository<Community, Long> repository() {
        return communityRepository;
    }
}
