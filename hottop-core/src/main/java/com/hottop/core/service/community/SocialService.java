package com.hottop.core.service.community;

import com.hottop.core.model.community.Social;
import com.hottop.core.model.zpoj.bean.BusinessEntityIndicator;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.community.SocialRepository;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialService extends EntityBaseService<Social, Long>{

    @Autowired
    private SocialRepository socialRepository;

    @Override
    public EntityBaseRepository<Social, Long> repository() {
        return socialRepository;
    }

    //查询是否已收藏
    public List<Social> findAllByUserId(Long userId) {
        return socialRepository.findAllByUserId(userId);
    }
}
