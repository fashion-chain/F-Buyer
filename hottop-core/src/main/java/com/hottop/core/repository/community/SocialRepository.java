package com.hottop.core.repository.community;

import com.hottop.core.model.community.Social;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialRepository extends EntityBaseRepository<Social, Long> {

    List<Social> findAllByUserId(Long userId);
}
