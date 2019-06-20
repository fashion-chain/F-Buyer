package com.hottop.api.cms;

import com.hottop.api.community.service.CommunityService;
import com.hottop.core.cms.service.PageService;
import com.hottop.core.model.cms.Page;
import com.hottop.core.model.community.Community;
import com.hottop.core.repository.community.CommunityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PageTest {

    @Autowired
    private PageService pageService;

    @Autowired
    private CommunityRepository communityRepository;

    @Test
    public void pageDecorateTest() {
        Optional<Community> optional = communityRepository.findById(2L);
        Community community = optional.isPresent() ? optional.get() : null;
        if (community != null && community.getPageId() != 0) {
            Page page = pageService.getPageByIdOrName(community.getPageId(), null);
            pageService.decorate(page, community);
        }
    }
}
